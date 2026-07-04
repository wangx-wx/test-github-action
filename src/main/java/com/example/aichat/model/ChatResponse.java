package com.example.aichat.model;

public record ChatResponse(
        String content,
        String model,
        String strategy,
        String conversationId
) {}
