package com.timlenny.backend.model.user;

public record MongoUserDTO(
        String username,
        String password
) {
}
