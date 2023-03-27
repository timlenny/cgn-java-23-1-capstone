package com.timlenny.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicDTO;
import com.timlenny.backend.model.topic.TopicPosition;
import com.timlenny.backend.repository.TopicRepository;
import com.timlenny.backend.service.topic.TopicConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
            "NONE",
            "HOME",
            List.of(),
            new TopicPosition(125, 250),
            "HOME",
            "HOME",
            3,
            true
    );
    TopicDTO demoTopicJavaDTO = new TopicDTO(
            "2", "HOME", "Java", 3, new TopicPosition(0, 0)
    );

    Topic demoTopicJava = new Topic(
            "2", "NONE", "Java", List.of(new Edge("3231", "", "")), new TopicPosition(200, 200), "", "", 3, true
    );

    @Test
    @WithMockUser(username = "user", password = "123")
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
    @WithMockUser(username = "user", password = "123")
    void whenGetAllTopicsAndRepoHasData_theReturnListOfAllTopics() throws Exception {
        topicRepository.save(demoTopicJava);
        String jsonObj = mapper.writeValueAsString(new Topic[]{demoTopicJava});
        mockMvc.perform(MockMvcRequestBuilders.get("/api/topic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonObj));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "user", password = "123")
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

    @Test
    @DirtiesContext
    @WithMockUser(username = "user", password = "123")
    void whenTopicDelete_theReturnDeletedTopicId() throws Exception {
        topicRepository.save(demoTopicHome);
        topicRepository.save(demoTopicJava);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/topic/" + demoTopicJava.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(demoTopicJava.getId()));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "user", password = "123")
    void whenTopicPositionUpdate_theReturnDeletedTopicCounter() throws Exception {
        topicRepository.save(demoTopicJava);

        String jsonObj = mapper.writeValueAsString(List.of(demoTopicJavaDTO));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/topic/positions")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(jsonObj))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().json("1"));
    }
}
