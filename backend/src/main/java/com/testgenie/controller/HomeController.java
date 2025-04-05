package com.testgenie.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>TestGenie API</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        line-height: 1.6;
                        max-width: 800px;
                        margin: 0 auto;
                        padding: 20px;
                    }
                    h1 {
                        color: #4f46e5;
                    }
                    .endpoint {
                        background-color: #f3f4f6;
                        padding: 10px;
                        border-radius: 5px;
                        margin-bottom: 10px;
                    }
                    code {
                        background-color: #e5e7eb;
                        padding: 2px 5px;
                        border-radius: 3px;
                    }
                </style>
            </head>
            <body>
                <h1>Welcome to TestGenie API</h1>
                <p>This is the backend API for TestGenie, a test case generator for LeetCode-style problems.</p>
                
                <h2>Available Endpoints:</h2>
                <div class="endpoint">
                    <strong>POST /api/v1/generate</strong> - Generate test cases based on constraints
                </div>
                <div class="endpoint">
                    <strong>GET /api/v1/constraints</strong> - Get available constraints and defaults
                </div>
                <div class="endpoint">
                    <strong>POST /api/v1/feedback</strong> - Submit feedback or bug reports
                </div>
                
                <h2>API Documentation:</h2>
                <p>For detailed API documentation, visit the <a href="/swagger-ui/index.html">Swagger UI</a>.</p>
                
                <h2>Frontend Application:</h2>
                <p>The frontend application is available at <a href="http://localhost:3000">http://localhost:3000</a> or <a href="https://testgenie.vercel.app">https://testgenie.vercel.app</a>.</p>
            </body>
            </html>
            """;
    }
} 