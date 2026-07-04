package com.example.aichat.service;

import com.example.aichat.model.ChatRequest;
import com.example.aichat.model.ChatResponse;
import com.example.aichat.model.ModelInfo;
import com.example.aichat.service.routing.ModelRouter;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 聊天编排服务。
 * 路由选择模型 → 调用 AI → 返回响应。
 */
@Service
public class ChatService {

    private final ModelRouter modelRouter;

    public ChatService(ModelRouter modelRouter) {
        this.modelRouter = modelRouter;
    }

    public ChatResponse chat(ChatRequest request) {
        String conversationId = request.conversationId() != null
                ? request.conversationId()
                : UUID.randomUUID().toString();

        ModelRouter.ResolvedModel resolved = modelRouter.resolve(request.message());

        String content = resolved.chatClient().prompt()
                .user(request.message())
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId))
                .call()
                .content();

        return new ChatResponse(content, resolved.modelName(), resolved.strategyName(), conversationId);
    }

    public List<ModelInfo> getAvailableModels() {
        return List.of(
                new ModelInfo("gpt-4o",
                        "Most capable model for complex reasoning, code generation, and multi-step tasks",
                        0.7),
                new ModelInfo("gpt-4o-mini",
                        "Fast and cost-effective model for general chat and simple Q&A",
                        0.9)
        );
    }
}
