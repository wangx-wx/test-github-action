package com.example.aichat.config;

import com.example.aichat.service.routing.DefaultRoutingStrategy;
import com.example.aichat.service.routing.IntentBasedRoutingStrategy;
import com.example.aichat.service.routing.KeywordBasedRoutingStrategy;
import com.example.aichat.service.routing.ModelRoutingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 路由策略配置：根据 app.routing.strategy 属性选择激活的策略。
 */
@Configuration
public class RoutingConfig {

    @Bean
    @Primary
    public ModelRoutingStrategy activeRoutingStrategy(
            IntentBasedRoutingStrategy intentBased,
            KeywordBasedRoutingStrategy keywordBased,
            DefaultRoutingStrategy defaultStrategy,
            @Value("${app.routing.strategy:default}") String strategyType) {
        return switch (strategyType.toLowerCase()) {
            case "intent-based" -> intentBased;
            case "keyword-based" -> keywordBased;
            case "default" -> defaultStrategy;
            default -> throw new IllegalStateException(
                    "Unknown routing strategy: " + strategyType
                    + ". Valid values: intent-based, keyword-based, default");
        };
    }
}
