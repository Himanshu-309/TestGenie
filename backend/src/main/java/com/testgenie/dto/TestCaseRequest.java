package com.testgenie.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestCaseRequest {
    @NotNull(message = "Data type is required")
    private DataType dataType;
    
    @Min(value = 1, message = "Size must be at least 1")
    private Integer size;
    
    private Integer minValue;
    private Integer maxValue;
    private Boolean allowDuplicates;
    private Boolean isSorted;
    private String charset;
    private Integer rows;
    private Integer columns;
    private OutputFormat outputFormat;
    private ElementType elementType;
    private Integer stringLength;
    private CaseType caseType;
    private Boolean allowSpecialChars;
    private Boolean allowSpaces;
    private Boolean allowNumbers;
    
    public enum DataType {
        ARRAY,
        STRING,
        MATRIX,
        TREE
    }
    
    public enum OutputFormat {
        JSON,
        CSV,
        PLAIN_TEXT
    }

    public enum ElementType {
        NUMBER,
        CHARACTER,
        STRING
    }
    
    public enum CaseType {
        LOWER,
        UPPER,
        MIXED
    }
} 