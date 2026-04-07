package com.iqspark.kafka;

import com.iqspark.model.LogAnalysisResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogResultProducer {

    private final KafkaTemplate<String, LogAnalysisResult> kafkaTemplate;

    public LogResultProducer(KafkaTemplate<String, LogAnalysisResult> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResult(LogAnalysisResult result) {
        kafkaTemplate.send("logs-analysis-topic", result);
        log.info("Published AI result to Kafka: {}", result);
    }
}