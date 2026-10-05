package com.app.springai.controller;

import com.app.springai.service.AIService;
import com.app.springai.utility.Helper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class AIController {

    @Autowired
    private AIService aiService;
    @Autowired
    AIService service;

    @Value("${app.weather.api.key}")
    private String weatherAPIKey;
    // Tool Calling
//    @GetMapping("/tool")
//    public ResponseEntity<String> chatWithTool(@RequestParam (value = "userInput", required = true) String userInput) {
//        String response = aiService.chatWithTool(userInput);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/tool")
    public ResponseEntity<String> chatWithTool(@RequestParam (value = "city", required = true) String city) {
        String response = aiService.chatWithTool(city);
        return ResponseEntity.ok(response);
    }



}
