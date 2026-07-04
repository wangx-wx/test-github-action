package com.example.aichat.service.routing;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 基于关键词匹配的路由策略。
 * 简单的关键词 → 模型映射表，命中即路由。
 */
@Component
public class KeywordBasedRoutingStrategy implements ModelRoutingStrategy {

    private static final Map<String, String> KEYWORD_MODEL_MAP = Map.ofEntries(
            Map.entry("代码", "gpt-4o"),
            Map.entry("code", "gpt-4o"),
            Map.entry("debug", "gpt-4o"),
            Map.entry("调试", "gpt-4o"),
            Map.entry("algorithm", "gpt-4o"),
            Map.entry("算法", "gpt-4o"),
            Map.entry("architecture", "gpt-4o"),
            Map.entry("架构", "gpt-4o"),
            Map.entry("math", "gpt-4o"),
            Map.entry("数学", "gpt-4o"),
            Map.entry("编程", "gpt-4o"),
            Map.entry("程序", "gpt-4o"),
            Map.entry("bug", "gpt-4o"),
            Map.entry("错误", "gpt-4o"),
            Map.entry("异常", "gpt-4o"),
            Map.entry("casual", "gpt-4o-mini"),
            Map.entry("闲聊", "gpt-4o-mini"),
            Map.entry("天气", "gpt-4o-mini"),
            Map.entry("joke", "gpt-4o-mini"),
            Map.entry("笑话", "gpt-4o-mini")
    );

    @Override
    public String selectModel(String message) {
        if (message == null) {
            return "gpt-4o-mini";
        }

        String lower = message.toLowerCase();
        for (var entry : KEYWORD_MODEL_MAP.entrySet()) {
            if (lower.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "gpt-4o-mini";
    }

    @Override
    public String getStrategyName() {
        return "keyword-based";
    }
}
