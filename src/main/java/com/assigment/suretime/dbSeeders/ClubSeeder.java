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
         Club club = new Club(address, "AZS UMCS Lublin");
         clubRepository.findByName(club.getName()).ifPresentOrElse(
                 c -> {log.info("Do not insert, club exist."+ club);},
                 () -> {log.info("Insered:"+ clubRepository.save(club));}
         );

           Address address2 = new Address("Stalowa Wola ",
                   "Plytka",
                   new BigDecimal(22),
                   new BigDecimal(1));
           Club club2 = new Club(address, "KKS Stalowa Wola");
           clubRepository.findByName(club.getName()).ifPresentOrElse(
                   c -> {log.info("Do not insert, club exist."+ club2);},
                   () -> {log.info("Insered:"+ clubRepository.save(club2));}
           );
       };
   }



}
