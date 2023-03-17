package com.timlenny.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timlenny.backend.model.Edge;
import com.timlenny.backend.model.Topic;
import com.timlenny.backend.model.TopicPosition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class StudyplanControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    Topic demoTopicStart = new Topic(
            "1", "START", List.of(new Edge("3231", "", "")), new TopicPosition(200, 200), "", "", 3, true
    );
    Topic demoTopicJava = new Topic(
            "2", "Java", List.of(new Edge("32343232141", "1", "2")), new TopicPosition(300, 600), "Pending", "1852", 3, true
    );

    @Test
    void whenGetAllTopics_theReturnListOfAllTopics() throws Exception {
        String jsonObj = mapper.writeValueAsString(new Topic[]{demoTopicStart, demoTopicJava});
        mockMvc.perform(MockMvcRequestBuilders.get("/api/topic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonObj));
    }


}
