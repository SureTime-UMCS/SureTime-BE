package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.club.Club;
import com.assigment.suretime.club.ClubRepository;
import com.assigment.suretime.address.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ClubSeeder {

    private static final Logger log = LoggerFactory.getLogger(ClubSeeder.class);

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

//    @Bean
//    CommandLineRunner initDatabase(CompetitorRepository competitorRepository){
//        return args -> {
//            Address address = new Address(" Kurzyna Mala",
//                    "Nie powiem :)",
//                    new BigDecimal(22),
//                    new BigDecimal(33));
//            String email = "zywko@gmail.com";
//            Competitor competitor = new Competitor(
//                    "Szymon",
//                    "Zywko",
//                    Gender.MALE,
//                    email,
//                    address,
//                    LocalDateTime.now());
//
//            competitorRepository.findCompetitorByEmail(email).
//                    ifPresentOrElse(c -> log.info("Do not inserted " + competitor),
//                            () -> {
//                                competitorRepository.insert(competitor);
//                                log.info("Inserted: " + competitor);
//                            });
//        };
//
//    }

}
