package com.example.aichat.service.routing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModelRouterTest {

    @Mock
    private ChatClient gpt4o;

    @Mock
    private ChatClient gpt4oMini;

    private ModelRoutingStrategy strategy;

    private ModelRouter router;

    @BeforeEach
    void setUp() {
        // Use a simple strategy: anything containing "code" → gpt-4o, else → gpt-4o-mini
        strategy = new KeywordBasedRoutingStrategy();
        router = new ModelRouter(gpt4o, gpt4oMini, strategy);
    }

    @Test
    void shouldRouteCodeMessageToGpt4o() {
        ModelRouter.ResolvedModel resolved = router.resolve("help me write code");
        assertThat(resolved.modelName()).isEqualTo("gpt-4o");
        assertThat(resolved.chatClient()).isEqualTo(gpt4o);
        assertThat(resolved.strategyName()).isEqualTo("keyword-based");
    }

    @Test
    void shouldRouteGeneralMessageToGpt4oMini() {
        ModelRouter.ResolvedModel resolved = router.resolve("Hello there!");
        assertThat(resolved.modelName()).isEqualTo("gpt-4o-mini");
        assertThat(resolved.chatClient()).isEqualTo(gpt4oMini);
        assertThat(resolved.strategyName()).isEqualTo("keyword-based");
    }

    @Test
    void shouldReturnActiveStrategyName() {
        assertThat(router.getActiveStrategyName()).isEqualTo("keyword-based");
    }

    @Test
    void shouldFallbackToFirstModelWhenUnknownModel() {
        // Create a router with a strategy that returns an unknown model
        ModelRouter router2 = new ModelRouter(gpt4o, gpt4oMini, message -> "unknown-model");
        ModelRouter.ResolvedModel resolved = router2.resolve("test");
        // Should fallback to an available model (not null) rather than crashing
        assertThat(resolved.chatClient()).isNotNull();
        assertThat(resolved.modelName()).isIn("gpt-4o", "gpt-4o-mini");
    }
}
