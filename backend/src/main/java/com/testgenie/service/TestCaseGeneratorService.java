package com.testgenie.service;

import com.testgenie.dto.TestCaseRequest;

public interface TestCaseGeneratorService {
    String generateTestCase(TestCaseRequest request);
    String getConstraints();
} 