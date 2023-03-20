package com.timlenny.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.model.topic.TopicPosition;
import com.timlenny.backend.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TopicControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TopicRepository topicRepository;
    ObjectMapper mapper = new ObjectMapper();
    Topic demoTopic = new Topic(
            "123456", "Test1", List.of(new Edge("123456", "", "")), new TopicPosition(200, 200), "", "", 3, true
    );
    TopicDTO demoTopicDTO = new TopicDTO(
            "123456", "Test1", 1
    );

    @Test
    void whenGetAllTopicsAndRepoIsEmpty_theReturnEmptyListOfAllTopics() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/topic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    @DirtiesContext
    void whenGetAllTopicsAndRepoHasData_theReturnListOfAllTopics() throws Exception {
        topicRepository.save(demoTopic);
        String jsonObj = mapper.writeValueAsString(new Topic[]{demoTopic});
        mockMvc.perform(MockMvcRequestBuilders.get("/api/topic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonObj));
    }

    @Test
    @DirtiesContext
    void whenAddNewTopic_thenReturnNewTopic() throws Exception {
        String jsonObj = mapper.writeValueAsString(demoTopicDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/topic")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(jsonObj))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(jsonPath("$.topicName").value("Test1"));
    }
}
