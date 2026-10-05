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
import org.springframework.web.client.RestClient;

import java.util.List;
@Configuration
public class AIConfig {

    @Bean
    public ChatMemory chatMemory() {
        InMemoryChatMemoryRepository repository =
                new InMemoryChatMemoryRepository();

        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryRepository(repository)
                .build();
    }

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory) {

        MessageChatMemoryAdvisor memoryAdvisor =
                MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder
                .defaultAdvisors(
                        memoryAdvisor,
                        new SimpleLoggerAdvisor(),
                        new SafeGuardAdvisor(List.of("games"))
                )
                .build();
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://api.weatherapi.com/")
                .build();
    }
}