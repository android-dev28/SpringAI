package com.app.springai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateLoaderImpl implements DataLoader{


    @Value("classpath:/Company_Policy_RAG_Practice.json")
    private Resource jsonResource;
    @Value("classpath:/employee_data.json")
    private Resource empJsonResource;

    @Value("classpath:/Company_Policy_RAG_Practice.pdf")
    private Resource pdfResource;

    @Override
    public List<Document> loadDocumentsFromJson() {
        return new JsonReader(empJsonResource,"name").read();
    }

    // TO DO
    @Override
    public List<Document> loadDocumentsFromPdf() {
        // Implementation for loading documents from PDF
        var pdfReader = new PagePdfDocumentReader(pdfResource, PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                        .withNumberOfBottomTextLinesToDelete(0)
                        .build())
                .withPagesPerDocument(1)
                .build());
        return pdfReader.read();
    }
}
