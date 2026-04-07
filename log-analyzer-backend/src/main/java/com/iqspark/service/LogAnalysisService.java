package com.iqspark.service;

import com.iqspark.client.OpenAIClient;
import com.iqspark.model.LogAnalysisResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogAnalysisService {

    private final OpenAIClient client;

    public LogAnalysisService(OpenAIClient client) {
        this.client = client;
    }

    public LogAnalysisResult analyze(String logs) {
        log.info("Analyzing logs: {}", logs);

        try {
            return client.analyzeLogs(logs);
        } catch (Exception e) {
            log.error("Error analyzing logs", e);
            throw new RuntimeException("AI processing failed");
        }
    }
}