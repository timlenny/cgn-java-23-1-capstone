package com.timlenny.backend.controller;

import com.timlenny.backend.model.user.MongoUser;
import com.timlenny.backend.model.user.MongoUserDTO;
import com.timlenny.backend.service.user.MongoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MongoUserController {

    private final MongoUserService mongoUserService;

    @GetMapping("/me")
    public String getMe1(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return "AnonymousUser";
    }

    @PostMapping
    public MongoUser create(@RequestBody MongoUserDTO user) {
        return mongoUserService.createNewUser(user);
    }
}
