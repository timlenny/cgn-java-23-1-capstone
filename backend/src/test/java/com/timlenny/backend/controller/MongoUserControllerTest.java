package com.timlenny.backend.controller;

import com.timlenny.backend.repository.MongoUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MongoUserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MongoUserRepository mongoUserRepository;

    @Test
    @DirtiesContext
    @WithMockUser(username = "user", password = "1234567")
    void getMe_whenAuthenticated_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user",
                            "password":"1234567"
                        }
                        """));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void create_whenValid_then200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "test1234",
                                    "password": "test1234"
                                }
                                """)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}
