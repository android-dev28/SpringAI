package com.app.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIServiceImpl implements AIService {

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessage;
    @Value("classpath:/prompts/user-message.st")
    private Resource userMessage;
    private ChatClient chatClient;
    private VectorStore vectorStore;
    public AIServiceImpl(ChatClient chatClient, VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
    }
    @Override
    public String chatWithAI(String userInput) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(userInput)
                .topK(3)
                .similarityThreshold(0.6)
                .build();
        // Load data from vector store and use it to generate a response based on the user input.
          List<Document> similarDocuments = this.vectorStore.similaritySearch(searchRequest);
          List<String> docList = similarDocuments.stream().map(Document::getText).toList();
          String contextData = String.join(", ", docList);
          System.out.println("contextData: " + contextData);
          //pass in context


//        return this.chatClient
//                .prompt()
//                .system(system->system.text(this.systemMessage).param("documents", contextData))
//                .user(user-> user.text(this.userMessage).param("userInput", userInput))
//                .call()
//                .content();
        return this.chatClient
                .prompt()
//                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .advisors(QuestionAnswerAdvisor.builder(vectorStore).searchRequest(SearchRequest.builder().topK(2).similarityThreshold(0.5).build()).build())
                .user(user-> user.text(this.userMessage).param("userInput", userInput))
                .call()
                .content();
//        return "You said (Service): " + userInput;
    }

    @Override
    public void saveDate(List<String> list) {
        List<Document> docList = list.stream().map(Document::new).toList();
        this.vectorStore.add(docList);
    }
}
