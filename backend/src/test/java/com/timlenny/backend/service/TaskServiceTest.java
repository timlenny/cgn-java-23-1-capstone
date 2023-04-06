package com.timlenny.backend.service;

import com.timlenny.backend.model.task.Task;
import com.timlenny.backend.repository.TaskRepository;
import com.timlenny.backend.service.task.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    TaskRepository taskRepository = mock(TaskRepository.class);
    TaskService taskService = new TaskService(taskRepository);
    Instant demoTime = Instant.parse("2022-04-01T10:00:00Z");
    Task demoTask1 = new Task("ID111", "SUBT222", "Title1", "Desc1", false, demoTime);

    @Test
    @DirtiesContext
    void isGetAllTasksReturnTasks_whenGetAllTasksIsCalled() {
        when(taskRepository.findBySubtopicId("SUBT222")).thenReturn(List.of(demoTask1));
        List<Task> actual = taskService.getAllTasksFromSubtopicId("SUBT222");
        assertEquals(actual, List.of(demoTask1));
    }
}