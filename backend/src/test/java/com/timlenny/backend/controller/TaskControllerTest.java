package com.timlenny.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timlenny.backend.model.task.Task;
import com.timlenny.backend.model.task.TaskDTO;
import com.timlenny.backend.model.task.TaskUpdateDTO;
import com.timlenny.backend.model.user.MongoUser;
import com.timlenny.backend.repository.MongoUserRepository;
import com.timlenny.backend.repository.TaskRepository;
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
class TaskControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MongoUserRepository mongoUserRepository;
    @Autowired
    TaskRepository taskRepository;
    Instant demoTime = Instant.parse("2022-04-01T10:00:00Z");
    TaskDTO demoTask1DTO = new TaskDTO("SUBT222", "Title1", "Desc1", false);
    TaskUpdateDTO demoTask1UpdateDTO = new TaskUpdateDTO("ID111", "Title1", "Desc1", false);
    Task demoTask1 = new Task("ID111", "SUBT222", "Title1", "Desc1", false, demoTime);

    @Test
    @WithMockUser(username = "user", password = "123")
    @DirtiesContext
    void whenGetAllTasksFromSubtopicId_ThenReturnListOfTasks() throws Exception {
        mongoUserRepository.save(new MongoUser("111", "user", "123", "BASIC", List.of()));
        taskRepository.save(demoTask1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/SUBT222"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.[0].id").value("ID111"))
                .andExpect(jsonPath("$.[0].subtopicId").value("SUBT222"))
                .andExpect(jsonPath("$.[0].title").value("Title1"))
                .andExpect(jsonPath("$.[0].desc").value("Desc1"))
                .andExpect(jsonPath("$.[0].isCompleted").value(false));

    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "user", password = "123")
    void whenAddNewTask_thenReturnNewTask() throws Exception {
        mongoUserRepository.save(new MongoUser("111", "user", "123", "BASIC", List.of("1")));
        taskRepository.save(demoTask1);
        String jsonObj = mapper.writeValueAsString(demoTask1DTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(jsonObj).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.subtopicId").value("SUBT222"))
                .andExpect(jsonPath("$.title").value("Title1"))
                .andExpect(jsonPath("$.desc").value("Desc1"))
                .andExpect(jsonPath("$.isCompleted").value(false));

    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "user", password = "123")
    void whenUpdateTask_isResponesUpdatedTask() throws Exception {
        mongoUserRepository.save(new MongoUser("111", "user", "123", "BASIC", List.of("1")));
        taskRepository.save(demoTask1);
        demoTask1UpdateDTO.setTitle("Update1");
        String jsonObj = mapper.writeValueAsString(demoTask1UpdateDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/tasks/update")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(jsonObj).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.subtopicId").value("SUBT222"))
                .andExpect(jsonPath("$.title").value("Update1"))
                .andExpect(jsonPath("$.desc").value("Desc1"))
                .andExpect(jsonPath("$.isCompleted").value(false));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "user", password = "123")
    void whenDeleteTask_isDeletedTaskIdReturned() throws Exception {
        mongoUserRepository.save(new MongoUser("111", "user", "123", "BASIC", List.of("1")));
        taskRepository.save(demoTask1);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/tasks/" + demoTask1.getId()).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(demoTask1.getId()));
    }
}