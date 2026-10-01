package com.app.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AIConfig {
    @Bean
    public ChatMemory chatMemory(){
        InMemoryChatMemoryRepository inMemoryChatMemoryRepository = new InMemoryChatMemoryRepository();
        return MessageWindowChatMemory.builder().maxMessages(10).chatMemoryRepository(inMemoryChatMemoryRepository).build();
    }

    @Bean
    public ChatClient chatClient (ChatClient.Builder chatClientBuilder, ChatMemory chatMemory){

        System.out.println("ChatMemoryImplementation class: " + chatMemory.getClass().getName());
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClientBuilder
                .defaultAdvisors(messageChatMemoryAdvisor, new SimpleLoggerAdvisor(),new SafeGuardAdvisor(List.of("games")))
                .defaultOptions(OllamaOptions.builder().model("qwen2.5-coder:7b").temperature(0.3).build())
                .build();

    }
}
