package com.timlenny.backend.controller;


import com.timlenny.backend.model.task.Task;
import com.timlenny.backend.model.task.TaskDTO;
import com.timlenny.backend.model.task.TaskUpdateDTO;
import com.timlenny.backend.service.task.TaskService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Task addNewTask(@RequestBody TaskDTO newTask) {
        return taskService.addNewTask(newTask);
    }

    @PostMapping("/update")
    public Task updateTask(@RequestBody TaskUpdateDTO taskToUpdate) {
        return taskService.updateTask(taskToUpdate);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable String id) {
        return taskService.deleteTask(id);
    }
}