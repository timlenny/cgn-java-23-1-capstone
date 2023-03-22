package com.timlenny.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.model.topic.TopicPosition;
import com.timlenny.backend.repository.TopicRepository;
import com.timlenny.backend.service.TopicConversionService;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TopicControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicConversionService topicConversionService;
    ObjectMapper mapper = new ObjectMapper();
    Topic demoTopicHome = new Topic(
            "1",
            "HOME",
            List.of(),
            new TopicPosition(125, 250),
            "HOME",
            "HOME",
            3,
            true
    );
    TopicDTO demoTopicJavaDTO = new TopicDTO(
            "HOME", "Java", 3
    );

    Topic demoTopicJava = new Topic(
            "2", "Java", List.of(new Edge("3231", "", "")), new TopicPosition(200, 200), "", "", 3, true
    );

    @Test
    void whenGetAllTopicsAndRepoIsEmpty_theReturnEmptyListOfAllTopics() throws Exception {
        String jsonObj = """
                   [
                               {
                                   "topicName": "HOME",
                                   "edges": [],
                                   "position": {
                                       "x": 125,
                                       "y": 250
                                   },
                                   "topicStatus": "HOME",
                                   "subtopicId": "HOME",
                                   "size": 3,
                                   "open": true
                               }
                           ]
                """;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/topic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().json(jsonObj));
    }

    @Test
    @DirtiesContext
    void whenGetAllTopicsAndRepoHasData_theReturnListOfAllTopics() throws Exception {
        topicRepository.save(demoTopicJava);
        String jsonObj = mapper.writeValueAsString(new Topic[]{demoTopicJava});
        mockMvc.perform(MockMvcRequestBuilders.get("/api/topic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonObj));
    }

    @Test
    @DirtiesContext
    void whenAddNewTopic_thenReturnNewTopic() throws Exception {
        String jsonObj = mapper.writeValueAsString(demoTopicJavaDTO);
        Optional<Topic> existCheck = topicRepository.findByTopicName("HOME");
        if (existCheck.isEmpty()) {
            topicRepository.save(demoTopicHome);
        }
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/topic")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(jsonObj))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.topicName").value("Java"));
    }
}
