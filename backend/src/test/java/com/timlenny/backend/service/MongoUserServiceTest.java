package com.timlenny.backend.service;

import com.timlenny.backend.model.user.MongoUser;
import com.timlenny.backend.model.user.MongoUserDTO;
import com.timlenny.backend.repository.MongoUserRepository;
import com.timlenny.backend.service.user.MongoUserDetailsService;
import com.timlenny.backend.service.user.MongoUserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MongoUserServiceTest {
    MongoUserRepository mongoUserRepository = mock(MongoUserRepository.class);
    IdService idService = mock(IdService.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    public final MongoUserService mongoUserService = new MongoUserService(mongoUserRepository, idService, passwordEncoder);
    public final MongoUserDetailsService mongoUserDetailsService = new MongoUserDetailsService(mongoUserRepository);
    MongoUserDTO userDTO = new MongoUserDTO("User", "Password");
    MongoUser user = new MongoUser("123", "User", "Password", "BASIC", List.of());

    @Test
    @DirtiesContext
    void isNewUserAddedCorrectly_WhenAddNewUser() {
        when(mongoUserRepository.existsByUsername(userDTO.username())).thenReturn(false);
        when(mongoUserRepository.save(user)).thenReturn(user);
        when(idService.generateId()).thenReturn("123");
        when(passwordEncoder.encode("Password")).thenReturn("Password");
        MongoUser actual = mongoUserService.createNewUser(userDTO);
        assertEquals(user.id(), actual.id());
        assertEquals(user.username(), actual.username());
    }

    @Test
    @DirtiesContext
    void isExceptionThrown_WhenUserExists() {
        when(mongoUserRepository.existsByUsername(userDTO.username())).thenReturn(true);
        when(mongoUserRepository.save(user)).thenReturn(user);
        when(idService.generateId()).thenReturn("123");
        when(passwordEncoder.encode("Password")).thenReturn("Password");
        String exceptionMsg = "No Exception";
        try {
            mongoUserService.createNewUser(userDTO);
        } catch (Exception ex) {
            exceptionMsg = ex.getMessage();
        }
        assertEquals("409 CONFLICT \"User already exists\"", exceptionMsg);
    }

    @Test
    @DirtiesContext
    void isExceptionThrown_WhenUsernameToShort() {
        MongoUserDTO userDTO = new MongoUserDTO("A", "123");
        when(mongoUserRepository.existsByUsername(userDTO.username())).thenReturn(false);
        String exceptionMsg = "No Exception";
        try {
            mongoUserService.createNewUser(userDTO);
        } catch (Exception ex) {
            exceptionMsg = ex.getMessage();
        }
        assertEquals("400 BAD_REQUEST \"Username must have at least 3 characters\"", exceptionMsg);
    }

    @Test
    @DirtiesContext
    void isExceptionThrown_WhenPasswordToShort() {
        MongoUserDTO userDTO = new MongoUserDTO("Abcdefg", "123");
        when(mongoUserRepository.existsByUsername(userDTO.username())).thenReturn(false);
        String exceptionMsg = "No Exception";
        try {
            mongoUserService.createNewUser(userDTO);
        } catch (Exception ex) {
            exceptionMsg = ex.getMessage();
        }
        assertEquals("400 BAD_REQUEST \"Password must have at least 7 characters\"", exceptionMsg);
    }

    @Test
    void isUsernameLoadingCorrectly_WhenLoadUsernameIsCalled() {
        when(mongoUserRepository.findByUsername(userDTO.username())).thenReturn(Optional.ofNullable(user));
        UserDetails actual = mongoUserDetailsService.loadUserByUsername(userDTO.username());
        assertEquals(user.username(), actual.getUsername());
    }

}
