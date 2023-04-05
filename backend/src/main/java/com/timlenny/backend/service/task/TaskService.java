package com.timlenny.backend.service.task;

import com.timlenny.backend.model.task.Task;
import com.timlenny.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasksFromSubtopicId(String id) {
        List<Task> tasksResult = taskRepository.findBySubtopicId(id);
        List<Task> tasks = new ArrayList<>(tasksResult);
        tasks.sort(Comparator.comparing(Task::getCreationDate));
        return tasks;
    }

}
