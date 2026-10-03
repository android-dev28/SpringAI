package com.app.springai.service;

import org.springframework.ai.document.Document;

import java.util.List;

public interface DataTransformerService {

    List<Document> transformDocuments(List<Document> documents);
}
