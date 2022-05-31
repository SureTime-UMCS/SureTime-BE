package com.assigment.suretime.competition.domain.repository;

import com.assigment.suretime.competition.domain.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompetitionRepository extends MongoRepository<Competition, String> {
    Optional<Competition> findByName(String name);
    void deleteByName(String name);
}
