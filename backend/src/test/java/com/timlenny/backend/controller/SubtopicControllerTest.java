package com.timlenny.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timlenny.backend.model.subtopic.Subtopic;
import com.timlenny.backend.model.subtopic.SubtopicDTO;
import com.timlenny.backend.model.topic.Edge;
import com.timlenny.backend.model.topic.Topic;
import com.timlenny.backend.model.topic.TopicPosition;
import com.timlenny.backend.model.user.MongoUser;
import com.timlenny.backend.repository.MongoUserRepository;
import com.timlenny.backend.repository.SubtopicRepository;
import com.timlenny.backend.repository.TopicRepository;
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

import java.time.Instant;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class SubtopicControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SubtopicRepository subtopicRepository;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MongoUserRepository mongoUserRepository;
    @Autowired
    TopicRepository topicRepository;

    Instant demoTime = Instant.parse("2022-04-01T10:00:00Z");
    Subtopic demoSubtopic1 = new Subtopic("1234", "1", 1, 1, demoTime, "Title", "Subtitle", "desc", Instant.now());
    SubtopicDTO demoSubtopic1DTO = new SubtopicDTO("1", 1, Instant.now(), "Title", "desc");
    Topic demoTopicJava = new Topic(
            "1", "1", "Java", List.of(new Edge("3231", "", "")), new TopicPosition(200, 200), "", List.of(""), 3, true
    );

    @Test
    @WithMockUser(username = "user", password = "123")
    @DirtiesContext
    void whenGetAllSubtopicsFromTopicId_ThenReturnListOfSubtopics() throws Exception {
        mongoUserRepository.save(new MongoUser("111", "user", "123", "BASIC", List.of()));
        subtopicRepository.save(demoSubtopic1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/subtopics/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1234"))
                .andExpect(jsonPath("$.[0].topicId").value("1"))
                .andExpect(jsonPath("$.[0].iconStatus").value(1))
                .andExpect(jsonPath("$.[0].position").value(1))
                .andExpect(jsonPath("$.[0].title").value("Title"))
                .andExpect(jsonPath("$.[0].subtitel").value("Subtitle"))
                .andExpect(jsonPath("$.[0].desc").value("desc"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "user", password = "123")
    void whenAddNewSubtopic_thenReturnNewSubtopic() throws Exception {
        mongoUserRepository.save(new MongoUser("111", "user", "123", "BASIC", List.of("1")));
        topicRepository.save(demoTopicJava);
        String jsonObj = mapper.writeValueAsString(demoSubtopic1DTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/subtopics")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(jsonObj).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.title").value("Java"))
                .andExpect(jsonPath("$.desc").value("desc"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "user", password = "123")
    void whenDeleteSubtopic_ReturnDeletedSubtopicId() throws Exception {
        mongoUserRepository.save(new MongoUser("111", "user", "123", "BASIC", List.of("1")));
        subtopicRepository.save(demoSubtopic1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/subtopics/" + demoSubtopic1.getId()).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().string(demoSubtopic1.getId()));
    }


    @Test
    @WithMockUser(username = "user", password = "123")
    @DirtiesContext
    void whenGetAllSubtopicsToday_ThenReturnListOfSubtopicsForToday() throws Exception {
        mongoUserRepository.save(new MongoUser("111", "user", "123", "BASIC", List.of("1")));
        topicRepository.save(demoTopicJava);
        subtopicRepository.save(demoSubtopic1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/subtopics/today"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1234"))
                .andExpect(jsonPath("$.[0].topicId").value("1"))
                .andExpect(jsonPath("$.[0].iconStatus").value(1))
                .andExpect(jsonPath("$.[0].position").value(1))
                .andExpect(jsonPath("$.[0].title").value("Title"))
                .andExpect(jsonPath("$.[0].subtitel").value("Subtitle"))
                .andExpect(jsonPath("$.[0].desc").value("desc"));
    }

    @Test
    @WithMockUser(username = "user", password = "123")
    @DirtiesContext
    void whenGetAllSubtopicsTodayWithUpcomingTrue_ThenReturnListOfSubtopicsForTodayUpcoming() throws Exception {
        mongoUserRepository.save(new MongoUser("111", "user", "123", "BASIC", List.of("1")));
        Instant demoTimeFuture = Instant.parse("2029-04-01T10:00:00Z");
        Subtopic demoSubtopic1Future = new Subtopic("1234", "1", 1, 1, demoTimeFuture, "Title", "Subtitle", "desc", Instant.now());

        topicRepository.save(demoTopicJava);
        subtopicRepository.save(demoSubtopic1Future);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/subtopics/upcoming"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1234"))
                .andExpect(jsonPath("$.[0].topicId").value("1"))
                .andExpect(jsonPath("$.[0].iconStatus").value(1))
                .andExpect(jsonPath("$.[0].position").value(1))
                .andExpect(jsonPath("$.[0].title").value("Title"))
                .andExpect(jsonPath("$.[0].subtitel").value("Subtitle"))
                .andExpect(jsonPath("$.[0].desc").value("desc"));
    }

}
