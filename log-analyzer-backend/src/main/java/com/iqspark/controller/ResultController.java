package com.iqspark.controller;

import com.iqspark.client.ResultStore;
import com.iqspark.model.LogAnalysisResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultStore store;

    public ResultController(ResultStore store) {
        this.store = store;
    }

    @GetMapping
    public List<LogAnalysisResult> getResults() {
        return store.getAll();
    }
}