package com.app.springai.service;

import org.springframework.stereotype.Service;

import java.util.List;


public interface AIService {
    String chatWithAI(String userInput);
    void saveDate(List<String> data);
}
