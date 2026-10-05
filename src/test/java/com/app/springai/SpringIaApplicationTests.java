package com.app.springai;

import com.app.springai.service.AIService;
import com.app.springai.utility.WeatherTool;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringIaApplicationTests {

    @Autowired
    WeatherTool weatherTool;
    @Test
    public void testWeatherTool() {
        String city = "New York";
        String response = weatherTool.getWeather(city);
        System.out.println("WeatherTool response: " + response);
        // Do something with the response, e.g., assert it contains expected information
    }


}
