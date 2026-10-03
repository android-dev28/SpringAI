package com.app.springai;

import com.app.springai.service.AIService;
import com.app.springai.service.DataLoader;
import com.app.springai.service.DataTransformerService;
import com.app.springai.utility.Helper;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringIaApplicationTests {


    @Autowired
    DataLoader loderService;
    @Autowired
    DataTransformerService transformerService;

    @Autowired
    VectorStore vectorStore;


    @Test
    void readDataFromJson(){
        var docs = loderService.loadDocumentsFromJson();
        for (Document doc : docs) {
            System.out.println("Document: " + doc.getText()+"/n");
        }
    }

    @Test
    void readDataFromPDF(){
        var docs = loderService.loadDocumentsFromPdf();
        docs.forEach(item-> {
            System.out.println(item);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        });
    }

    @Test
    void transformDataFromPDF(){
        var transDocs = transformerService.transformDocuments(loderService.loadDocumentsFromPdf());
        transDocs.forEach(item-> {
            System.out.println(item);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        });
        System.out.println("Number of transformed documents: " + transDocs.size());

        // stored document in vector
        vectorStore.add(transDocs);
        IO.println("Stored document in vector store");
    }

}
