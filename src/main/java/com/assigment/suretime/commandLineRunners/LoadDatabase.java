package com.assigment.suretime.commandLineRunners;

import com.assigment.suretime.common.Gender;
import com.assigment.suretime.competitor.Address;
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
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
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
