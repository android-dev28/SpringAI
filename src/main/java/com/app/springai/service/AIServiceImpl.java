package com.app.springai.service;

import com.app.springai.utility.SimpleDateTimeTool;
import com.app.springai.utility.WeatherTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AIServiceImpl implements AIService {

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessage;
    @Value("classpath:/prompts/user-message.st")
    private Resource userMessage;
    private ChatClient chatClient;

    @Autowired
    private RestClient restClient;
    public AIServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String chatWithTool(String input) {
        System.out.println("Getting current date and time using SimpleDateTimeTool...");
        return chatClient
                .prompt()
                .tools(new SimpleDateTimeTool(), new WeatherTool(restClient))
                .user(input)
                .call()
                .content();
    }
}
