package com.example.aichat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAI 配置：为每个模型创建独立的 ChatClient Bean。
 * ChatMemory 由 Spring AI ChatMemoryAutoConfiguration 自动提供。
 */
@Configuration
public class OpenAiConfig {

    @Bean
    public MessageChatMemoryAdvisor chatMemoryAdvisor(ChatMemory chatMemory) {
        return MessageChatMemoryAdvisor.builder(chatMemory).build();
    }

    @Bean
    @Qualifier("gpt4o")
    public ChatClient gpt4oChatClient(ChatClient.Builder builder, MessageChatMemoryAdvisor memoryAdvisor) {
        return builder
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("gpt-4o")
                        .temperature(0.7)
                        .build())
                .defaultAdvisors(memoryAdvisor)
                .build();
    }

    @Bean
    @Qualifier("gpt4oMini")
    public ChatClient gpt4oMiniChatClient(ChatClient.Builder builder, MessageChatMemoryAdvisor memoryAdvisor) {
        return builder
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("gpt-4o-mini")
                        .temperature(0.9)
                        .build())
                .defaultAdvisors(memoryAdvisor)
                .build();
    }
}
