package com.testgenie.controller;

import com.testgenie.dto.TestCaseRequest;
import com.testgenie.service.TestCaseGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Test Case Generator", description = "APIs for generating test cases")
@CrossOrigin(origins = {"http://localhost:3000", "https://testgenie.vercel.app"})
public class TestCaseController {

    private static final Logger logger = LoggerFactory.getLogger(TestCaseController.class);
    private final TestCaseGeneratorService testCaseGeneratorService;

    @Autowired
    public TestCaseController(TestCaseGeneratorService testCaseGeneratorService) {
        this.testCaseGeneratorService = testCaseGeneratorService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate test cases based on constraints")
    public ResponseEntity<String> generateTestCase(@Valid @RequestBody TestCaseRequest request) {
        logger.info("Received request: dataType={}, elementType={}, size={}, charset={}", 
            request.getDataType(), request.getElementType(), request.getSize(), request.getCharset());
        String result = testCaseGeneratorService.generateTestCase(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/constraints")
    @Operation(summary = "Get available constraints and defaults")
    public ResponseEntity<String> getConstraints() {
        String constraints = testCaseGeneratorService.getConstraints();
        return ResponseEntity.ok(constraints);
    }

    @PostMapping("/feedback")
    @Operation(summary = "Submit feedback or bug reports")
    public ResponseEntity<String> submitFeedback(@RequestBody String feedback) {
        // TODO: Implement feedback handling
        return ResponseEntity.ok("Feedback received");
    }
} 