package com.example.aichat.service.routing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 默认路由策略：所有消息路由到同一个模型（兜底策略）。
 */
@Component
public class DefaultRoutingStrategy implements ModelRoutingStrategy {

    private final String defaultModel;

    public DefaultRoutingStrategy(
            @Value("${spring.ai.openai.chat.options.model:gpt-4o-mini}") String defaultModel) {
        this.defaultModel = defaultModel;
    }

    @Override
    public String selectModel(String message) {
        return defaultModel;
    }

    @Override
    public String getStrategyName() {
        return "default";
    }
}
