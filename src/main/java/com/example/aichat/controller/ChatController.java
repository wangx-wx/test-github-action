package com.example.aichat.controller;

import com.example.aichat.model.ChatRequest;
import com.example.aichat.model.ChatResponse;
import com.example.aichat.service.ChatService;
import com.example.aichat.service.routing.ModelRouter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;
    private final ModelRouter modelRouter;

    public ChatController(ChatService chatService, ModelRouter modelRouter) {
        this.chatService = chatService;
        this.modelRouter = modelRouter;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = chatService.chat(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/models")
    public ResponseEntity<Map<String, Object>> getModels() {
        return ResponseEntity.ok(Map.of(
                "models", chatService.getAvailableModels(),
                "activeStrategy", modelRouter.getActiveStrategyName()
        ));
    }
}
