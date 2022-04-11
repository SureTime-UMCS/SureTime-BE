package com.assigment.suretime.person;

import com.assigment.suretime.person.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {
    Optional<Person> findByEmail(String email);


}