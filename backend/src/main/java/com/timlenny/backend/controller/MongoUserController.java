package com.timlenny.backend.controller;

import com.timlenny.backend.model.user.MongoUser;
import com.timlenny.backend.model.user.MongoUserDTO;
import com.timlenny.backend.service.user.MongoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MongoUserController {

    private final MongoUserService mongoUserService;

    @PostMapping("/login")
    public MongoUser login(Principal principal) {
        return getMe1(principal);
    }

    @PostMapping
    public MongoUser create(@RequestBody MongoUserDTO user) {
        return mongoUserService.createNewUser(user);
    }

    @GetMapping("/me")
    public MongoUser getMe1(Principal principal) {
        return mongoUserService.loadMongoUserFromDB(principal);
    }

    @GetMapping("/me2")
    public String getMe2() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
