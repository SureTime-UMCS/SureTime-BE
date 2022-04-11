package com.assigment.suretime.dbSeeders;


import com.assigment.suretime.securityJwt.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Configuration
@Slf4j
public class SeederEntryPoint implements ISeeder{

    private final ClubSeeder clubSeeder;
    private final UserPersonSeeder userPersonSeeder;
    private final RoleSeeder roleSeeder;

    @Override
    public void seed() {
           roleSeeder.seed();
           clubSeeder.seed();
           userPersonSeeder.seed();
    }

    @Override
    public void resetDb() {
        roleSeeder.resetDb();
        clubSeeder.resetDb();
        userPersonSeeder.resetDb();
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            resetDb();
            seed();
        };
    }
}
