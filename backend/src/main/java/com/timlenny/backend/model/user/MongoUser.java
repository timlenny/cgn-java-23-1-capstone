package com.timlenny.backend.model.user;

import org.springframework.data.annotation.Id;

import java.util.List;

public record MongoUser(
        @Id
        String id,
        String username,
        String password,
        String role,
        List<String> topicIds
) {
}
