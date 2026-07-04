package com.example.aichat.service.routing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntentBasedRoutingStrategyTest {

    private IntentBasedRoutingStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new IntentBasedRoutingStrategy();
    }

    @Test
    void shouldRouteCodeQuestionToGpt4o() {
        String message = "Write a Java function to sort a list using quicksort algorithm";
        assertThat(strategy.selectModel(message)).isEqualTo("gpt-4o");
    }

    @Test
    void shouldRouteDebugQuestionToGpt4o() {
        String message = "I have a NullPointerException in my Spring Boot application, how do I debug it?";
        assertThat(strategy.selectModel(message)).isEqualTo("gpt-4o");
    }

    @Test
    void shouldRouteCodeSnippetToGpt4o() {
        String message = "```java\npublic class Test {\n    public static void main(String[] args) {\n        System.out.println(\"Hello\");\n    }\n}\n```";
        assertThat(strategy.selectModel(message)).isEqualTo("gpt-4o");
    }

    @Test
    void shouldRouteLongMessageToGpt4o() {
        String message = "A".repeat(250) + " explain how this works in detail";
        assertThat(strategy.selectModel(message)).isEqualTo("gpt-4o");
    }

    @Test
    void shouldRouteSimpleChatToGpt4oMini() {
        String message = "Hello, how are you today?";
        assertThat(strategy.selectModel(message)).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldRouteWeatherQuestionToGpt4oMini() {
        String message = "What's the weather like today?";
        assertThat(strategy.selectModel(message)).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldHandleNullMessage() {
        assertThat(strategy.selectModel(null)).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldHandleBlankMessage() {
        assertThat(strategy.selectModel("   ")).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldReturnCorrectStrategyName() {
        assertThat(strategy.getStrategyName()).isEqualTo("intent-based");
    }
}
