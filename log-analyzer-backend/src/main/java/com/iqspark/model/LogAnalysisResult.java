package com.iqspark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogAnalysisResult {
    private String summary;
    private List<String> errors;
    private List<String> fixes;
}