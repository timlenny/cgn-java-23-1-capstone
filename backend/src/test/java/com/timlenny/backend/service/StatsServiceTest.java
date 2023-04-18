package com.timlenny.backend.service;

import com.timlenny.backend.model.stats.Stats;
import com.timlenny.backend.model.user.MongoUser;
import com.timlenny.backend.repository.StatsRepository;
import com.timlenny.backend.service.stats.StatsService;
import com.timlenny.backend.service.user.MongoUserService;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatsServiceTest {

    StatsRepository statsRepository = mock(StatsRepository.class);
    MongoUserService mongoUserService = mock(MongoUserService.class);
    StatsService statsService = new StatsService(mongoUserService, statsRepository);

    @Test
    @DirtiesContext
    void isGetStreakReturningCorrectStreakData_whenNoStatsExists() {
        when(mongoUserService.getCurrentUser()).thenReturn(Optional.of(new MongoUser("123", "User123", "123", "BASIC", List.of())));
        List<Boolean> actual = statsService.getStreak();
        assertEquals(1, actual.stream().filter(b -> b).count());
    }

    @Test
    @DirtiesContext
    void isGetStreakReturningCorrectStreakData_whenStatsExists() {
        when(mongoUserService.getCurrentUser()).thenReturn(Optional.of(new MongoUser("123", "User123", "123", "BASIC", List.of())));
        when(statsRepository.findById("123")).thenReturn(Optional.of(new Stats("123", List.of(Instant.now().plus(2, ChronoUnit.DAYS)))));
        List<Boolean> actual = statsService.getStreak();
        boolean greaterThanOrEqual = actual.stream().anyMatch(b -> b);
        assertTrue(greaterThanOrEqual);
    }

}
