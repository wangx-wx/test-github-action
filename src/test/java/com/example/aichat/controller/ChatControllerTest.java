package com.example.aichat.controller;

import com.example.aichat.model.ChatRequest;
import com.example.aichat.model.ChatResponse;
import com.example.aichat.service.ChatService;
import com.example.aichat.service.routing.ModelRouter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public ChatService chatService() {
            ChatService service = mock(ChatService.class);
            when(service.getAvailableModels()).thenReturn(List.of());
            when(service.chat(any())).thenReturn(
                    new ChatResponse("Hello!", "gpt-4o", "intent-based", "conv-123"));
            return service;
        }

        @Bean
        @Primary
        public ModelRouter modelRouter() {
            ModelRouter router = mock(ModelRouter.class);
            when(router.getActiveStrategyName()).thenReturn("intent-based");
            return router;
        }
    }

    @Test
    void shouldReturn400WhenMessageIsBlank() throws Exception {
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenMessageIsMissing() throws Exception {
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnChatResponse() throws Exception {
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"Hello\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello!"))
                .andExpect(jsonPath("$.model").value("gpt-4o"))
                .andExpect(jsonPath("$.strategy").value("intent-based"))
                .andExpect(jsonPath("$.conversationId").value("conv-123"));
    }

    @Test
    void shouldReturnModels() throws Exception {
        mockMvc.perform(get("/api/models"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeStrategy").value("intent-based"))
                .andExpect(jsonPath("$.models").isArray());
    }
}
