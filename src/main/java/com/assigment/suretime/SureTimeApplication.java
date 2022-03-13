package com.assigment.suretime;

import com.assigment.suretime.competitor.Address;
import com.assigment.suretime.competitor.Competitor;
import com.assigment.suretime.competitor.CompetitorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
@Configuration
@EnableSwagger2
public class SureTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SureTimeApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(CompetitorRepository competitorRepository, MongoTemplate mongoTemplate){
        return args -> {
            Address address = new Address(" Kurzyna Mala",
                    "Nie powiem :)",
                    new BigDecimal(22),
                    new BigDecimal(33));
            String email = "zywko2@gmail.com";
            Competitor competitor = new Competitor(
                    "Szymon",
                    "Zywko",
                    Gender.MALE,
                    email,
                    address,
                    LocalDateTime.now());

//            usingMongoTemplateAndQuery(competitorRepository, mongoTemplate, email, competitor);
            competitorRepository.findCompetitorByEmail(email).ifPresentOrElse(c->{
                System.out.println("Competitor already exist.");
            }, () ->{
                System.out.println("Insert competitor");
                competitorRepository.insert(competitor);
            });
        };

    }
}
