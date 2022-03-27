package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.securityJwt.models.ERole;
import com.assigment.suretime.securityJwt.models.Role;
import com.assigment.suretime.securityJwt.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class RoleSeeder{

    private static final Logger log = LoggerFactory.getLogger(RoleSeeder.class);


    @Bean
    public static CommandLineRunner seedRoles(RoleRepository repository) {
        return args -> {
            Arrays.stream(ERole.values()).forEach(eRole ->
                    repository.findByName(eRole)
                            .ifPresentOrElse(role -> {log.info("Role: " + eRole.toString() + " already exist.");
                                        },
                                    () -> {
                                        repository.save(new Role(eRole));
                                        log.info("Inserted role: " + eRole.toString());
                                    }));
        };
    }
}
