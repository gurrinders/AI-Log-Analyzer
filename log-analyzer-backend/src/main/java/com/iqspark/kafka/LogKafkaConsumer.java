package com.iqspark.kafka;

import com.iqspark.client.ResultStore;
import com.iqspark.model.LogAnalysisResult;
import com.iqspark.service.LogAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogKafkaConsumer {

    private final LogAnalysisService service;
    private final LogResultProducer producer;
    private final ResultStore store;

    public LogKafkaConsumer(LogAnalysisService service, LogResultProducer producer, ResultStore store) {
        this.service = service;
        this.producer = producer;
        this.store = store;
    }

    @KafkaListener(topics = "logs-topic", groupId = "log-analyzer-group")
    public void consume(String logMessage) {
        log.info("Received log from Kafka: {}", logMessage);
        try {
            LogAnalysisResult result = service.analyze(logMessage);
            store.add(result);   // ✅ store for UI
            producer.sendResult(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("AI Analysis Result completed");
    }
}
