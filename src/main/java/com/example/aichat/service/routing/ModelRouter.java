package com.example.aichat.service.routing;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 模型路由器。
 * 持有策略引用，根据消息内容选择对应的 ChatClient。
 */
@Service
public class ModelRouter {

    private final Map<String, ChatClient> modelClientMap;
    private final ModelRoutingStrategy strategy;

    public ModelRouter(
            @Qualifier("gpt4o") ChatClient gpt4o,
            @Qualifier("gpt4oMini") ChatClient gpt4oMini,
            ModelRoutingStrategy strategy) {
        this.modelClientMap = Map.of(
                "gpt-4o", gpt4o,
                "gpt-4o-mini", gpt4oMini
        );
        this.strategy = strategy;
    }

    /**
     * 根据消息内容解析出对应的 ChatClient 和元数据。
     */
    public ResolvedModel resolve(String message) {
        String modelName = strategy.selectModel(message);
        ChatClient client = modelClientMap.get(modelName);
        if (client == null) {
            // 安全回退：选择第一个可用模型
            modelName = modelClientMap.keySet().iterator().next();
            client = modelClientMap.get(modelName);
        }
        return new ResolvedModel(client, modelName, strategy.getStrategyName());
    }

    public String getActiveStrategyName() {
        return strategy.getStrategyName();
    }

    /**
     * 路由结果：包含选中的 ChatClient 和元数据。
     */
    public record ResolvedModel(
            ChatClient chatClient,
            String modelName,
            String strategyName
    ) {}
}
