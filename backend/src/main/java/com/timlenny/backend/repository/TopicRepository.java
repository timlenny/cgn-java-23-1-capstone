package com.timlenny.backend.repository;

import com.timlenny.backend.model.topic.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    Optional<Topic> findByTopicName(String topicName);
}