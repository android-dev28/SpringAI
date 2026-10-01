package com.app.springai;

import com.app.springai.service.AIService;
import com.app.springai.utility.Helper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringIaApplicationTests {

	@Test
	void contextLoads() {
	}

    @Autowired
    AIService service;
    @Test
    void saveDataToVectorDB(){

    }
}
