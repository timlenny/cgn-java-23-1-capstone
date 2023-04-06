package com.timlenny.backend.service.task;

import com.timlenny.backend.model.task.Task;
import com.timlenny.backend.model.task.TaskDTO;
import com.timlenny.backend.model.task.TaskUpdateDTO;
import com.timlenny.backend.repository.TaskRepository;
import com.timlenny.backend.service.IdService;
import com.timlenny.backend.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final IdService idService;
    private final TimeService timeService;

    public TaskService(TaskRepository taskRepository, IdService idService, TimeService timeService) {
        this.taskRepository = taskRepository;
        this.idService = idService;
        this.timeService = timeService;
    }

    public List<Task> getAllTasksFromSubtopicId(String id) {
        List<Task> tasksResult = taskRepository.findBySubtopicId(id);
        List<Task> tasks = new ArrayList<>(tasksResult);
        tasks.sort(Comparator.comparing(Task::getCreationDate));
        return tasks;
    }

    public Task addNewTask(TaskDTO newTaskDTO) {
        Task newTask = new Task(
                idService.generateId(),
                newTaskDTO.getSubtopicId(),
                newTaskDTO.getTitle(),
                newTaskDTO.getDesc(),
                newTaskDTO.getIsCompleted(),
                timeService.getCurrentTime()
        );
        return taskRepository.save(newTask);
    }

    public Task updateTask(TaskUpdateDTO taskToUpdate) {
        Optional<Task> updateTask = taskRepository.findById(taskToUpdate.getId());
        if (updateTask.isPresent()) {
            Task updatedTask = updateTask.get();
            updatedTask.setTitle(taskToUpdate.getTitle());
            updatedTask.setDesc(taskToUpdate.getDesc());
            updatedTask.setIsCompleted(taskToUpdate.getIsCompleted());
            return taskRepository.save(updatedTask);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task to update not found!");
        }
    }
}