package com.iqspark.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqspark.model.LogAnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Service
public class OpenAIClient {

    @Value("${openai.api.key}")
    private String key;

    private WebClient webClient;

    @Autowired
    public OpenAIClient(WebClient webClient){
        this.webClient = webClient;
    }
    @Retryable(
            value = { Exception.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000))
    public LogAnalysisResult analyzeLogs(String logs) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(key);
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> request = getMapHttp(logs, headers);
        LogAnalysisResult result;
        try {
            String response = webClient.post()
                    .uri("/responses")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Step 1: Parse full response
            JsonNode root = new ObjectMapper().readTree(response);
            // Step 2: Extract text output
            String content = root
                    .path("output")
                    .get(0)
                    .path("content")
                    .get(0)
                    .path("text")
                    .asText();

            int start = content.indexOf("{");
            int end = content.indexOf("}") + 1; // +1 to include the closing brace
            String resultJson = content.substring(start, end);
            // Step 3: Convert JSON string → DTO
            result = objectMapper.readValue(resultJson, LogAnalysisResult.class);
        } catch (HttpClientErrorException httpEx) {
            if (httpEx.getStatusCode() == TOO_MANY_REQUESTS) {
                throw new RuntimeException("Quota exceeded. Check OpenAI billing.");
            } else
                throw httpEx;
        } catch (JsonMappingException e) {
            throw new RuntimeException("Failed to parse OpenAI response", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse OpenAI response", e);
        }
        return result;
    }

    private static Map<String, Object> getMapHttp(String logs, HttpHeaders headers) {
        String promptTemplate = """
                        Analyze logs and return STRICT JSON:
                        {
                            "summary": "...",
                            "errors": ["..."],
                            "fixes": ["..."]
                        }
                        
                        Return only JSON.
                        
                        Logs:
                        %s
                        """;

        String prompt = String.format(promptTemplate, logs);
        Map<String, Object> request = Map.of("model", "gpt-4.1-mini","input", prompt);
        return request;
    }
}