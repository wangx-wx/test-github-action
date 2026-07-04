package com.example.aichat.service.routing;

@FunctionalInterface
public interface ModelRoutingStrategy {

    /**
     * 根据消息内容选择最合适的模型
     *
     * @param message 用户消息
     * @return 模型名称，如 "gpt-4o" 或 "gpt-4o-mini"
     */
    String selectModel(String message);

    /**
     * 策略名称，用于响应元数据
     */
    default String getStrategyName() {
        return this.getClass().getSimpleName()
                .replace("RoutingStrategy", "")
                .replaceAll("([a-z])([A-Z])", "$1-$2")
                .toLowerCase();
    }
}
