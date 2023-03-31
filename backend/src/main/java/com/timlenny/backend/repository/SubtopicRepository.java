package com.timlenny.backend.repository;

import com.timlenny.backend.model.subtopic.Subtopic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubtopicRepository extends MongoRepository<Subtopic, String> {
    List<Subtopic> findByTopicId(String topicId);
}
