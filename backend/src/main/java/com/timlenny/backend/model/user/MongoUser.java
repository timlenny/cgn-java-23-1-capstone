package com.timlenny.backend.model.user;

import org.springframework.data.annotation.Id;

public record MongoUser(
        @Id
        String id,
        String username,
        String password,
        String role
) {
}
