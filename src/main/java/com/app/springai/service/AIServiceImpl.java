package com.app.springai.service;

import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService {

    @Override
    public String chatWithAI(String userInput) {
        // Implement your AI logic here. For now, we'll just echo the input.
        return "You said (Service): " + userInput;
    }
}
