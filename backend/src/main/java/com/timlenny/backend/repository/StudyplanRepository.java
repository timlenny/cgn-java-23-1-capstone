package com.timlenny.backend.repository;

import com.timlenny.backend.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyplanRepository extends MongoRepository<Topic, String> {
}