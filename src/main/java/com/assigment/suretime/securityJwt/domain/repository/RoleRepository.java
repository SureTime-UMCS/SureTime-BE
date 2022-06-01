package com.assigment.suretime.securityJwt.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.assigment.suretime.securityJwt.domain.models.ERole;
import com.assigment.suretime.securityJwt.domain.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);


}
