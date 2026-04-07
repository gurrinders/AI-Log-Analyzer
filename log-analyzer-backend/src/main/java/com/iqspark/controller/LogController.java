package com.iqspark.controller;

import com.iqspark.model.LogAnalysisResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.iqspark.service.LogAnalysisService;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogAnalysisService service;

    public LogController(LogAnalysisService service) {
        this.service = service;
    }

    @PostMapping("/analyze")
    public LogAnalysisResult analyzeLogs(@RequestBody String logs) {
        return service.analyze(logs);
    }
}