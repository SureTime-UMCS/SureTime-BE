package com.assigment.suretime.securityJwt.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.assigment.suretime.securityJwt.domain.models.User;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
  Optional<User> findByUserUUID(String uuid);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);


}
