package com.app.springai.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;


public interface AIService {
    String chatWithAI(String userInput);
    void saveDate(List<String> data);
    Flux<String> getResponse(String query);
}
