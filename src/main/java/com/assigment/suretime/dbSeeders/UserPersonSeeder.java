package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.person.PersonRepository;
import com.assigment.suretime.securityJwt.controllers.AuthController;
import com.assigment.suretime.securityJwt.models.ERole;
import com.assigment.suretime.securityJwt.models.Role;
import com.assigment.suretime.securityJwt.models.User;
import com.assigment.suretime.securityJwt.payload.request.SignupRequest;
import com.assigment.suretime.securityJwt.repository.RoleRepository;
import com.assigment.suretime.securityJwt.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.assigment.suretime.dbSeeders.SeederUtils.AssureRolesExistsInDb;

@AllArgsConstructor
@Component
@Slf4j
public class UserPersonSeeder implements ISeeder {

    AuthController authController;
    RoleRepository roleRepository;
    UserRepository userRepository;
    PersonRepository personRepository;

    public void seed(){
        log.info("Seeding UserPerson objects.");
        AssureRolesExistsInDb(roleRepository);


        assertAdminAndModAndUserCreation(authController, userRepository);
        Faker fake = new Faker();
        for (int i = 0; i < 10; i++) {
            String login = fake.name().username();
            SignupRequest request = new SignupRequest(login, login+"@gmail.com", "password");

            authController.registerUser(request);
        }

    }

    @Override
    public void resetDb() {
        log.info("Reseting all db.");
        personRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();
        log.info("Reset db end.");
    }

    private void assertAdminAndModAndUserCreation(AuthController authController, UserRepository userRepository) {
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
        Role mod = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
        Role admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();

        if (userRepository.findByUsername("admin").isEmpty()) {
            authController.registerUser(new SignupRequest("admin", "admin@gmail.com", "password"));
            User adminUser = userRepository.findByEmail("admin@gmail.com").orElseThrow(() -> new NotFoundException("User", "admin@gmail.com"));
            adminUser.setRoles(Set.of(admin, mod, userRole));
            userRepository.save(adminUser);
        }
        if (userRepository.findByUsername("mod").isEmpty()) {
            authController.registerUser(new SignupRequest("mod", "mod@gmail.com", "password"));
            User modUser = userRepository.findByEmail("mod@gmail.com").orElseThrow(() -> new NotFoundException("User", "mod@gmail.com"));
            modUser.setRoles(Set.of(mod, userRole));
            userRepository.save(modUser);
        }
        if (userRepository.findByUsername("user").isEmpty()) {
            authController.registerUser(new SignupRequest("user", "user@gmail.com", "password"));
            User user = userRepository.findByEmail("user@gmail.com").orElseThrow(() -> new NotFoundException("User", "user@gmail.com"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }

    }

}

