package com.timlenny.backend.service.stats;

import com.timlenny.backend.model.stats.Stats;
import com.timlenny.backend.model.user.MongoUser;
import com.timlenny.backend.repository.StatsRepository;
import com.timlenny.backend.service.user.MongoUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@Service
public class StatsService {

    MongoUserService mongoUserService;
    StatsRepository statsRepository;

    public StatsService(MongoUserService mongoUserService, StatsRepository statsRepository) {
        this.mongoUserService = mongoUserService;
        this.statsRepository = statsRepository;
    }

    public List<Boolean> getStreak() {
        Optional<MongoUser> user = mongoUserService.getCurrentUser();
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User for streak not found!");
        }
        Optional<Stats> userStatsOpt = statsRepository.findById(user.get().id());
        Stats userStats = userStatsOpt.orElseGet(() -> new Stats(user.get().id(), List.of()));
        List<Instant> userLoginDateImut = userStats.getLoginDates();
        List<Instant> userLoginDate = new ArrayList<>(userLoginDateImut);
        userLoginDate.add(Instant.now());
        List<Instant> streakInstantWeak = new ArrayList<>(List.of());
        List<Boolean> streakBoolean;

        LocalDate today = LocalDate.now();
        LocalDate startOfWeak = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeak = today.with(DayOfWeek.SUNDAY);

        ZonedDateTime startZonedDateTime = startOfWeak.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime endZonedDateTime = endOfWeak.atTime(23, 59, 59).atZone(ZoneId.systemDefault());

        Instant startInstant = startZonedDateTime.toInstant();
        Instant endInstant = endZonedDateTime.toInstant();

        for (Instant lDate : userLoginDate) {

            if (lDate.isBefore(Instant.from(startInstant)) || lDate.isAfter(Instant.from(endInstant))) {
                userLoginDate.remove(lDate);
            } else {
                streakInstantWeak.add(lDate);
            }
        }

        streakBoolean = IntStream.range(0, 7)
                .mapToObj(startOfWeak::plusDays)
                .map(actualDayLD -> streakInstantWeak.stream()
                        .map(d -> d.atZone(ZoneId.systemDefault()).toLocalDate())
                        .anyMatch(d -> d.equals(actualDayLD)))
                .toList();

        userStats.setLoginDates(userLoginDate);
        statsRepository.save(userStats);
        return streakBoolean;
    }
}
