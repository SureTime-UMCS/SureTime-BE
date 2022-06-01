package com.assigment.suretime.person.domain.repository;

import com.assigment.suretime.person.domain.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {
    Optional<Person> findByEmail(String email);
    Optional<Person> findByUserUUID(String uuid);



}