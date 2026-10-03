package com.app.springai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DataTransformerServiceImpl implements DataTransformerService {
    @Override
    public List<Document> transformDocuments(List<Document> documents) {
        // Implementation for transforming documents
        return new TokenTextSplitter(
                300,
                400,
                10,
                5000,
                true)
                .transform(documents);
    }

}
