package com.example.aichat.service.routing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KeywordBasedRoutingStrategyTest {

    private KeywordBasedRoutingStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new KeywordBasedRoutingStrategy();
    }

    @Test
    void shouldRouteChineseKeywordToGpt4o() {
        assertThat(strategy.selectModel("帮我写一段代码")).isEqualTo("gpt-4o");
    }

    @Test
    void shouldRouteCodeKeywordToGpt4o() {
        assertThat(strategy.selectModel("I need to debug my code")).isEqualTo("gpt-4o");
    }

    @Test
    void shouldRouteAlgorithmKeywordToGpt4o() {
        assertThat(strategy.selectModel("Explain this algorithm to me")).isEqualTo("gpt-4o");
    }

    @Test
    void shouldRouteCasualKeywordToGpt4oMini() {
        assertThat(strategy.selectModel("Let's have a casual chat")).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldRouteJokeKeywordToGpt4oMini() {
        assertThat(strategy.selectModel("Tell me a joke")).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldRouteWeatherToGpt4oMini() {
        assertThat(strategy.selectModel("今天天气怎么样")).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldDefaultToGpt4oMiniWhenNoKeywordMatches() {
        assertThat(strategy.selectModel("Hello, nice to meet you")).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldHandleNullMessage() {
        assertThat(strategy.selectModel(null)).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldReturnCorrectStrategyName() {
        assertThat(strategy.getStrategyName()).isEqualTo("keyword-based");
    }
}
