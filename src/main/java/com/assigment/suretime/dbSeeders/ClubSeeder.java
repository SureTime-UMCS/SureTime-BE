package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.club.Club;
import com.assigment.suretime.club.ClubRepository;
import com.assigment.suretime.address.Address;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@Slf4j
@AllArgsConstructor
public class ClubSeeder implements ISeeder {

    ClubRepository clubRepository;


    @Override
    public void seed() {

        Faker fake = new Faker(new Locale("pl_PL"));
        List<Club> clubs = new ArrayList<>();

        clubs.add(new Club(new Address("Lublin",
                "Gleboka",
                new BigDecimal(31),
                new BigDecimal(0)),
                "AZS UMCS Lublin"));

        clubs.add(new Club(new Address("Stalowa Wola ",
                "Plytka",
                new BigDecimal(22),
                new BigDecimal(1)),
                "KKS Stalowa Wola"));


        for (int i = 0; i < 10; i++) {
            Club club = new Club(
                    new Address(fake.address().city(),
                            fake.address().streetName(),
                            new BigDecimal(fake.address().buildingNumber()),
                            new BigDecimal(fake.address().buildingNumber())),
                    fake.company().name());
            clubs.add(club);
        }
        for (var club : clubs) {
            clubRepository.findByName(club.getName()).ifPresentOrElse(
                    c -> {
                        log.info("Do not insert, club exist." + club);
                    },
                    () -> {
                        log.info("Insered:" + clubRepository.save(club));
                    }
            );
        }



    }

    @Override
    public void resetDb() {
        log.info("Reseting all db.");
        clubRepository.deleteAll();
        log.info("End reseting db.");

    }
}
