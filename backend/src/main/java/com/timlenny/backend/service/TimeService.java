package com.timlenny.backend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TimeService {
    public Instant getCurrentTime() {
        return Instant.now();
    }
}
