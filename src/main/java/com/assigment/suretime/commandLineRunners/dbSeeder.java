package com.assigment.suretime.commandLineRunners;

import com.assigment.suretime.club.Club;
import com.assigment.suretime.club.ClubRepository;
import com.assigment.suretime.person.Gender;
import com.assigment.suretime.address.Address;
import com.assigment.suretime.competitor.Competitor;
import com.assigment.suretime.competitor.CompetitorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class dbSeeder {

    private static final Logger log = LoggerFactory.getLogger(dbSeeder.class);

    @Bean
    CommandLineRunner addClubs(ClubRepository clubRepository){
        return args -> {
          Address address = new Address("Lublin",
                  "Gleboka",
                  new BigDecimal(31),
                  new BigDecimal(0));
          Club club = new Club(address, "AZS22");
          clubRepository.findByName(club.getName()).ifPresentOrElse(
                  c -> {log.info("Do not insert, club exist."+ club);},
                  () -> {log.info("Insered:"+ clubRepository.insert(club));}
          );
        };
    }

    @Bean
    CommandLineRunner initDatabase(CompetitorRepository competitorRepository){
        return args -> {
            Address address = new Address(" Kurzyna Mala",
                    "Nie powiem :)",
                    new BigDecimal(22),
                    new BigDecimal(33));
            String email = "zywko@gmail.com";
            Competitor competitor = new Competitor(
                    "Szymon",
                    "Zywko",
                    Gender.MALE,
                    email,
                    address,
                    LocalDateTime.now());

            competitorRepository.findCompetitorByEmail(email).
                    ifPresentOrElse(c -> log.info("Do not inserted " + competitor),
                            () -> {
                                competitorRepository.insert(competitor);
                                log.info("Inserted: " + competitor);
                            });
        };

    }

}
