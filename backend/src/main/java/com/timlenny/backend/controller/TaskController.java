package com.timlenny.backend.controller;


import com.timlenny.backend.model.task.Task;
import com.timlenny.backend.service.task.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public List<Task> getAllTasksFromSubtopicId(@PathVariable String id) {
        return taskService.getAllTasksFromSubtopicId(id);
    }
}