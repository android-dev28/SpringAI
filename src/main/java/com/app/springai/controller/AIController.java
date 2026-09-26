package com.app.springai.controller;

import com.app.springai.service.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class AIController {

    @Autowired
    private AIService aiService;

    private ChatClient chatClient;

    AIController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


    @GetMapping
    public ResponseEntity<String> chatWithAI(@RequestParam (value = "userInput", required = true) String userInput) {
        var result = chatClient.prompt(userInput).call().content();
//        String response = aiService.chatWithAI(userInput);
        return ResponseEntity.ok(result);
    }

}
