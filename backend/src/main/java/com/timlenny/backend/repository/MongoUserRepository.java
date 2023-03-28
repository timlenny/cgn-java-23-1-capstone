package com.timlenny.backend.repository;

import com.timlenny.backend.model.user.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
    Optional<MongoUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
