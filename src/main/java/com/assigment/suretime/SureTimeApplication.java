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
}
