package com.iqspark.kafka;

import com.iqspark.model.LogAnalysisResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogResultConsumer {

    @KafkaListener(topics = "logs-analysis-topic", groupId = "ui-group")
    public void consumeResult(LogAnalysisResult result) {
        log.info("Final AI Result received: {}", result);
    }
}