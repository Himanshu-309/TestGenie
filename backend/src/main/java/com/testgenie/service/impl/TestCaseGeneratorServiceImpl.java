package com.testgenie.service.impl;

import com.testgenie.dto.TestCaseRequest;
import com.testgenie.service.TestCaseGeneratorService;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestCaseGeneratorServiceImpl implements TestCaseGeneratorService {

    @Override
    public String generateTestCase(TestCaseRequest request) {
        switch (request.getDataType()) {
            case ARRAY:
                return generateArray(request);
            case STRING:
                return generateString(request);
            case MATRIX:
                return generateMatrix(request);
            case TREE:
                return generateTree(request);
            default:
                throw new IllegalArgumentException("Unsupported data type");
        }
    }

    @Override
    public String getConstraints() {
        return """
            {
                "dataTypes": ["ARRAY", "STRING", "MATRIX", "TREE"],
                "outputFormats": ["JSON", "CSV", "PLAIN_TEXT"],
                "defaults": {
                    "array": {
                        "size": 10,
                        "minValue": -100,
                        "maxValue": 100,
                        "allowDuplicates": true,
                        "isSorted": false
                    },
                    "string": {
                        "length": 10,
                        "charset": "abcdefghijklmnopqrstuvwxyz"
                    },
                    "matrix": {
                        "rows": 3,
                        "columns": 3,
                        "minValue": -100,
                        "maxValue": 100
                    }
                }
            }
            """;
    }

    private String generateArray(TestCaseRequest request) {
        List<Object> elements = new ArrayList<>();
        Random random = new Random();
        
        // Set default elementType to NUMBER if not specified
        TestCaseRequest.ElementType elementType = request.getElementType() != null ? 
            request.getElementType() : TestCaseRequest.ElementType.NUMBER;
        
        for (int i = 0; i < request.getSize(); i++) {
            Object value = switch (elementType) {
                case NUMBER -> generateNumber(random, request);
                case CHARACTER -> generateCharacter(random, request);
                case STRING -> generateRandomString(random, request);
                default -> throw new IllegalArgumentException("Unsupported element type: " + elementType);
            };
            
            if (!request.getAllowDuplicates() && elements.contains(value)) {
                i--; // Try again
                continue;
            }
            
            elements.add(value);
        }
        
        if (request.getIsSorted()) {
            Collections.sort(elements, (a, b) -> {
                if (a instanceof String && b instanceof String) {
                    return ((String) a).compareTo((String) b);
                }
                if (a instanceof Character && b instanceof Character) {
                    return ((Character) a).compareTo((Character) b);
                }
                return ((Number) a).intValue() - ((Number) b).intValue();
            });
        }
        
        return formatOutput(elements, request.getOutputFormat());
    }

    private Number generateNumber(Random random, TestCaseRequest request) {
        return random.nextInt(
            request.getMaxValue() - request.getMinValue() + 1
        ) + request.getMinValue();
    }

    private Character generateCharacter(Random random, TestCaseRequest request) {
        StringBuilder charsetBuilder = new StringBuilder();
        
        // Add letters based on case type
        if (request.getCaseType() == null || request.getCaseType() == TestCaseRequest.CaseType.LOWER) {
            charsetBuilder.append("abcdefghijklmnopqrstuvwxyz");
        } else if (request.getCaseType() == TestCaseRequest.CaseType.UPPER) {
            charsetBuilder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        } else if (request.getCaseType() == TestCaseRequest.CaseType.MIXED) {
            charsetBuilder.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        
        // Add numbers if allowed
        if (Boolean.TRUE.equals(request.getAllowNumbers())) {
            charsetBuilder.append("0123456789");
        }
        
        // Add special characters if allowed
        if (Boolean.TRUE.equals(request.getAllowSpecialChars())) {
            charsetBuilder.append("!@#$%^&*()_+-=[]{}|;:,.<>?");
        }
        
        // Add space if allowed
        if (Boolean.TRUE.equals(request.getAllowSpaces())) {
            charsetBuilder.append(" ");
        }
        
        // Use custom charset if provided
        String charset = request.getCharset() != null ? 
            request.getCharset() : charsetBuilder.toString();
            
        if (charset.isEmpty()) {
            charset = "abcdefghijklmnopqrstuvwxyz"; // Default to lowercase if no charset
        }
        
        return charset.charAt(random.nextInt(charset.length()));
    }

    private String generateRandomString(Random random, TestCaseRequest request) {
        StringBuilder charsetBuilder = new StringBuilder();
        
        // Add letters based on case type
        if (request.getCaseType() == null || request.getCaseType() == TestCaseRequest.CaseType.LOWER) {
            charsetBuilder.append("abcdefghijklmnopqrstuvwxyz");
        } else if (request.getCaseType() == TestCaseRequest.CaseType.UPPER) {
            charsetBuilder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        } else if (request.getCaseType() == TestCaseRequest.CaseType.MIXED) {
            charsetBuilder.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        
        // Add numbers if allowed
        if (Boolean.TRUE.equals(request.getAllowNumbers())) {
            charsetBuilder.append("0123456789");
        }
        
        // Add special characters if allowed
        if (Boolean.TRUE.equals(request.getAllowSpecialChars())) {
            charsetBuilder.append("!@#$%^&*()_+-=[]{}|;:,.<>?");
        }
        
        // Add space if allowed
        if (Boolean.TRUE.equals(request.getAllowSpaces())) {
            charsetBuilder.append(" ");
        }

        // Add custom charset if provided
        if (request.getCharset() != null && !request.getCharset().isEmpty()) {
            charsetBuilder.append(request.getCharset());
        }
        
        String charset = charsetBuilder.toString();
        if (charset.isEmpty()) {
            charset = "abcdefghijklmnopqrstuvwxyz"; // Default to lowercase if no charset
        }
        
        int length = request.getStringLength() != null ? request.getStringLength() : request.getSize();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(charset.charAt(random.nextInt(charset.length())));
        }
        return sb.toString();
    }

    private String generateString(TestCaseRequest request) {
        StringBuilder charsetBuilder = new StringBuilder();
        
        // Add letters based on case type
        if (request.getCaseType() == null || request.getCaseType() == TestCaseRequest.CaseType.LOWER) {
            charsetBuilder.append("abcdefghijklmnopqrstuvwxyz");
        } else if (request.getCaseType() == TestCaseRequest.CaseType.UPPER) {
            charsetBuilder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        } else if (request.getCaseType() == TestCaseRequest.CaseType.MIXED) {
            charsetBuilder.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        
        // Add numbers if allowed
        if (Boolean.TRUE.equals(request.getAllowNumbers())) {
            charsetBuilder.append("0123456789");
        }
        
        // Add special characters if allowed
        if (Boolean.TRUE.equals(request.getAllowSpecialChars())) {
            charsetBuilder.append("!@#$%^&*()_+-=[]{}|;:,.<>?");
        }
        
        // Add space if allowed
        if (Boolean.TRUE.equals(request.getAllowSpaces())) {
            charsetBuilder.append(" ");
        }
        
        // Use custom charset if provided
        String charset = request.getCharset() != null ? 
            request.getCharset() : charsetBuilder.toString();
            
        if (charset.isEmpty()) {
            charset = "abcdefghijklmnopqrstuvwxyz"; // Default to lowercase if no charset
        }
        
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < request.getSize(); i++) {
            sb.append(charset.charAt(random.nextInt(charset.length())));
        }
        
        return formatOutput(sb.toString(), request.getOutputFormat());
    }

    private String generateMatrix(TestCaseRequest request) {
        int[][] matrix = new int[request.getRows()][request.getColumns()];
        Random random = new Random();
        
        for (int i = 0; i < request.getRows(); i++) {
            for (int j = 0; j < request.getColumns(); j++) {
                matrix[i][j] = random.nextInt(
                    request.getMaxValue() - request.getMinValue() + 1
                ) + request.getMinValue();
            }
        }
        
        return formatOutput(matrix, request.getOutputFormat());
    }

    private String generateTree(TestCaseRequest request) {
        // TODO: Implement tree generation
        return "Tree generation not implemented yet";
    }

    private String formatOutput(Object data, TestCaseRequest.OutputFormat format) {
        if (format == null) {
            format = TestCaseRequest.OutputFormat.JSON;
        }
        
        return switch (format) {
            case JSON -> formatAsJson(data);
            case CSV -> formatAsCsv(data);
            case PLAIN_TEXT -> formatAsPlainText(data);
        };
    }

    private String formatAsJson(Object data) {
        try {
            if (data instanceof List) {
                List<?> list = (List<?>) data;
                if (list.isEmpty()) {
                    return "[]";
                }
                
                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < list.size(); i++) {
                    Object element = list.get(i);
                    if (element instanceof String) {
                        sb.append("\"").append(element).append("\"");
                    } else if (element instanceof Character) {
                        sb.append("'").append(element).append("'");
                    } else {
                        sb.append(element);
                    }
                    if (i < list.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                return sb.toString();
            } else if (data instanceof int[][]) {
                return new com.fasterxml.jackson.databind.ObjectMapper()
                    .writeValueAsString(data);
            } else {
                return "\"" + data.toString() + "\"";
            }
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Failed to convert data to JSON", e);
        }
    }

    private String formatAsCsv(Object data) {
        if (data instanceof List) {
            List<?> list = (List<?>) data;
            return list.stream()
                .map(element -> {
                    if (element instanceof String) {
                        return "\"" + element + "\"";
                    } else if (element instanceof Character) {
                        return "'" + element + "'";
                    }
                    return element.toString();
                })
                .collect(Collectors.joining(","));
        } else if (data instanceof int[][]) {
            return Arrays.stream((int[][]) data)
                .map(row -> Arrays.stream(row)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"));
        } else {
            return data.toString();
        }
    }

    private String formatAsPlainText(Object data) {
        if (data instanceof List) {
            List<?> list = (List<?>) data;
            return list.stream()
                .map(element -> {
                    if (element instanceof String) {
                        return "\"" + element + "\"";
                    } else if (element instanceof Character) {
                        return "'" + element + "'";
                    }
                    return element.toString();
                })
                .collect(Collectors.joining(" "));
        } else if (data instanceof int[][]) {
            return Arrays.stream((int[][]) data)
                .map(row -> Arrays.stream(row)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
        } else {
            return data.toString();
        }
    }
} 