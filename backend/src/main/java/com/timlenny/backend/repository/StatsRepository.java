package com.timlenny.backend.repository;

import com.timlenny.backend.model.stats.Stats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsRepository extends MongoRepository<Stats, String> {
}
