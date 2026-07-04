package com.example.aichat.service.routing;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultRoutingStrategyTest {

    @Test
    void shouldAlwaysReturnDefaultModel() {
        DefaultRoutingStrategy strategy = new DefaultRoutingStrategy("gpt-4o-mini");
        assertThat(strategy.selectModel("Write code")).isEqualTo("gpt-4o-mini");
        assertThat(strategy.selectModel("Hello")).isEqualTo("gpt-4o-mini");
        assertThat(strategy.selectModel(null)).isEqualTo("gpt-4o-mini");
    }

    @Test
    void shouldReturnCorrectStrategyName() {
        DefaultRoutingStrategy strategy = new DefaultRoutingStrategy("gpt-4o-mini");
        assertThat(strategy.getStrategyName()).isEqualTo("default");
    }
}
