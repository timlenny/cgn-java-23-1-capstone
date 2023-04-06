package com.timlenny.backend.service;

import com.timlenny.backend.model.task.Task;
import com.timlenny.backend.model.task.TaskDTO;
import com.timlenny.backend.model.task.TaskUpdateDTO;
import com.timlenny.backend.repository.TaskRepository;
import com.timlenny.backend.service.tasks.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    TaskRepository taskRepository = mock(TaskRepository.class);
    IdService idService = mock(IdService.class);
    TimeService timeService = mock(TimeService.class);
    TaskService taskService = new TaskService(taskRepository, idService, timeService);
    Instant demoTime = Instant.parse("2022-04-01T10:00:00Z");
    TaskDTO demoTask1DTO = new TaskDTO("SUBT222", "Title1", "Desc1", false);
    TaskUpdateDTO demoTask1UpdateDTO = new TaskUpdateDTO("ID111", "Title1", "Desc1", false);
    Task demoTask1 = new Task("ID111", "SUBT222", "Title1", "Desc1", false, demoTime);

    @Test
    @DirtiesContext
    void isGetAllTasksReturnTasks_whenGetAllTasksIsCalled() {
        when(taskRepository.findBySubtopicId("SUBT222")).thenReturn(List.of(demoTask1));
        List<Task> actual = taskService.getAllTasksFromSubtopicId("SUBT222");
        assertEquals(actual, List.of(demoTask1));
    }

    @Test
    @DirtiesContext
    void isNewTaskAddedCorrect_whenAddNewTask() {
        when(idService.generateId()).thenReturn("ID111");
        when(timeService.getCurrentTime()).thenReturn(demoTime);
        when(taskRepository.save(demoTask1)).thenReturn(demoTask1);

        Task actual = taskService.addNewTask(demoTask1DTO);

        assertEquals(actual, demoTask1);
    }

    @Test
    @DirtiesContext
    void isTaskUpdatedCorrectly_whenUpdateTask() {
        when(taskRepository.findById(demoTask1.getId())).thenReturn(Optional.ofNullable(demoTask1));
        when(taskRepository.save(demoTask1)).thenReturn(demoTask1);
        demoTask1.setTitle("Updated1");
        Task actual = taskService.updateTask(demoTask1UpdateDTO);
        assertEquals(actual, demoTask1);
    }

    @Test
    @DirtiesContext
    void isTaskUpdatedErrorThrownCorrectly_whenUpdateTaskNotExists() {
        when(taskRepository.findById(demoTask1.getId())).thenReturn(Optional.empty());
        when(taskRepository.save(demoTask1)).thenReturn(demoTask1);
        String error = "NO ERROR";
        try {
            taskService.updateTask(demoTask1UpdateDTO);
        } catch (Exception ex) {
            error = ex.getMessage();
        }
        assertEquals("400 BAD_REQUEST \"Task to update not found!\"", error);
    }
}
