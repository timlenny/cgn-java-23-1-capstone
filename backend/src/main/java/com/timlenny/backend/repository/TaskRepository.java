package com.timlenny.backend.repository;

import com.timlenny.backend.model.task.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findBySubtopicId(String subtopicId);
}