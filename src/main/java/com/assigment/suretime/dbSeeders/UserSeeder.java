package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.person.Person;
import com.assigment.suretime.securityJwt.models.ERole;
import com.assigment.suretime.securityJwt.models.Role;
import com.assigment.suretime.securityJwt.models.User;
import com.assigment.suretime.securityJwt.repository.RoleRepository;
import com.assigment.suretime.securityJwt.repository.UserRepository;
import com.assigment.suretime.securityJwt.security.jwt.AuthEntryPointJwt;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class UserSeeder {
    private static final Logger log = LoggerFactory.getLogger(AuthEntryPointJwt.class);

//    @Bean
//    CommandLineRunner addUsers(UserRepository repository, PasswordEncoder encoder, RoleRepository roleRepository) {
//        return args -> {
//            List<User> users = new ArrayList<>(Arrays.asList(
//                    new User("user", "user@gmail.com", encoder.encode("password"), new HashSet<>(List.of(roleRepository.findByName(ERole.ROLE_USER).get()))),
//                    new User("mod", "mod@gmail.com", encoder.encode("password"),  new HashSet<>(List.of(roleRepository.findByName(ERole.ROLE_USER).get())))
//            ));
//            users.forEach(user -> repository.findByUsername(user.getUsername())
//                    .ifPresentOrElse(u -> log.info("User: " + user.getUsername() + " already exist."),
//                            () -> {
//                                log.info("Inserted:" + user.getUsername());
//                                repository.save(user);
//                            }));
//        };
//
//    }
}
