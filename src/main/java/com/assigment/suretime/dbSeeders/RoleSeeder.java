package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.securityJwt.models.ERole;
import com.assigment.suretime.securityJwt.models.Role;
import com.assigment.suretime.securityJwt.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@AllArgsConstructor
public class RoleSeeder implements ISeeder {


    RoleRepository roleRepository;

    @Override
    public void seed() {
        Arrays.stream(ERole.values()).forEach(eRole ->
                roleRepository.findByName(eRole)
                        .ifPresentOrElse(role -> {log.info("Role: " + eRole.toString() + " already exist.");
                                },
                                () -> {
                                    roleRepository.save(new Role(eRole));
                                    log.info("Inserted role: " + eRole.toString());
                                }));

    }

    @Override
    public void resetDb() {
        roleRepository.deleteAll();
    }

}
