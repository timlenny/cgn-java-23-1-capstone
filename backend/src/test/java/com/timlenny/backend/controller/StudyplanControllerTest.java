package com.timlenny.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class StudyplanControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void whenGetAllTopics_theReturnListOfAllTopics() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/topic"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}
