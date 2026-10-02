package com.app.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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

        // RAGAdviser
        var adviser = RetrievalAugmentationAdvisor.builder().documentRetriever(VectorStoreDocumentRetriever
                .builder().vectorStore(vectorStore)
                .topK(3)
                .similarityThreshold(0.5)
                .build())
                .queryAugmenter(ContextualQueryAugmenter.builder().allowEmptyContext(false).build())
                .build();

        return this.chatClient
                .prompt()
//                .advisors(new QuestionAnswerAdvisor(vectorStore))
//                .advisors(QuestionAnswerAdvisor.builder(vectorStore).searchRequest(SearchRequest.builder().topK(2).similarityThreshold(0.5).build()).build())
                .advisors(adviser)
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

    @Override
    public Flux<String> getResponse(String query) {
        // RAG Pipeline
        // Pre-Retrieval: Use the query to retrieve relevant documents from the vector store.
        var adviser = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(
                        RewriteQueryTransformer.builder()
                                .chatClientBuilder(chatClient.mutate().clone())
                                .build(),
                        TranslationQueryTransformer.builder().chatClientBuilder(chatClient.mutate().clone()).targetLanguage("hi").build()
                )
                .queryExpander(MultiQueryExpander.builder().chatClientBuilder(chatClient.mutate().clone()).numberOfQueries(3).build())
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .topK(3)
                        .similarityThreshold(0.5)
                        .build())
                .documentJoiner(new ConcatenationDocumentJoiner())
                .queryAugmenter(ContextualQueryAugmenter.builder().build())
                .build();

        MultiQueryExpander expander =
                MultiQueryExpander.builder()
                        .chatClientBuilder(chatClient.mutate().clone())
                        .numberOfQueries(3)
                        .build();

        List<Query> queries = expander.expand(
                new Query(query)
        );

        queries.forEach(q ->
                System.out.println("QUERY: " + q.text())
        );

        return chatClient
                .prompt()
                .advisors(adviser)
                .user(query)
                .stream()
                .content();
    }
}
