package com.app.springai.controller;

import com.app.springai.service.AIService;
import com.app.springai.utility.Helper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class AIController {

    @Autowired
    private AIService aiService;
    @Autowired
    AIService service;

//    private ChatClient chatClient;
//
//    AIController(ChatClient.Builder builder) {
//        this.chatClient = builder.build();
//    }


    @GetMapping()
    public ResponseEntity<String> chatWithAI(@RequestParam (value = "userInput", required = true) String userInput) {
//        var result = chatClient.prompt(userInput).call().content();
        String response = aiService.chatWithAI(userInput);
        return ResponseEntity.ok(response);
    }
    @PostMapping  public ResponseEntity<String> saveData() {
        System.out.println("Saving data to vector database...");
        service.saveDate(Helper.getData());
        return ResponseEntity.ok("Data saved to vector database successfully.");
    }

}
