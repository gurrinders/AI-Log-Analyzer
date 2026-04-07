package com.iqspark.client;

import com.iqspark.model.LogAnalysisResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ResultStore {

    private final List<LogAnalysisResult> results = new CopyOnWriteArrayList<>();

    public void add(LogAnalysisResult result) {
        results.add(result);
    }

    public List<LogAnalysisResult> getAll() {
        return results;
    }
}