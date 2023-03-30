package com.timlenny.backend.repository;

import com.timlenny.backend.model.topic.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    Optional<Topic> findByTopicName(String topicName);

    List<Topic> findByParentId(String parentId);

    @Query("{ 'topicName': ?0, '_id': { '$in': ?1 } }")
    Optional<Topic> findByTopicNameAndIdIn(String topicName, List<String> ids);
}