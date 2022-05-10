package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.address.Address;
import com.assigment.suretime.competition.models.Competition;
import com.assigment.suretime.competition.CompetitionRepository;
import com.assigment.suretime.event.Event;
import com.assigment.suretime.event.EventRepository;
import com.assigment.suretime.heat.models.Heat;
import com.assigment.suretime.heat.HeatRepository;
import com.assigment.suretime.person.PersonRepository;
import com.assigment.suretime.person.models.Person;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static com.assigment.suretime.dbSeeders.SeederUtils.getFakeAddress;

@Component
@Slf4j
public class CompetitonSeeder implements ISeeder {

    final private PersonRepository personRepository;
    final private EventRepository eventRepository;
    final private HeatRepository heatRepository;
    final private CompetitionRepository competitionRepository;
    final private Faker fake;
    private List<Person> allPersons;


    public CompetitonSeeder(PersonRepository personRepository,
                            EventRepository eventRepository,
                            HeatRepository heatRepository,
                            CompetitionRepository competitionRepository
    ) {
        this.personRepository = personRepository;
        this.eventRepository = eventRepository;
        this.heatRepository = heatRepository;
        this.competitionRepository = competitionRepository;
        this.fake = new Faker(new Locale("pl_PL"));
        this.allPersons = personRepository.findAll();
    }

    @Override
    public void seed() {
        assert allPersons.size() > 30 : "Create at least 30 persons in UserPersonSeeder";

        List<String> competitionNames = List.of("Memorial Kamili Skomilowskiej",
                "Żylewicz", "Copernicus Cup", "European Athletics U23 Championship",
                "HMP 2022", "MMP 20222");
        List<String> eventNames = List.of("800m", "1500m", "3000m", "Rzut młotem",
                "Skok o tyczce", "100m", "200m", "Skok w dal", "trojskok", "4x400m");

        for (var competitionName : competitionNames) {
            Address address = getFakeAddress(fake);
            LocalDateTime startTime = LocalDateTime.of(2022,
                    Month.of(fake.random().nextInt(1, 12)), fake.random().nextInt(1, 29), 18, 0, 0);
            LocalDateTime endTime = LocalDateTime.of(2022,
                    startTime.getMonth(), startTime.getDayOfMonth(), 22, 0, 0);
            Competition competition = new Competition(null, competitionName, address,new HashSet<>(),
                    new HashSet<>(),startTime, endTime);
            Set<String> competitorsEmails = allPersons.subList(0, 30).stream().map(Person::getEmail).collect(Collectors.toSet());
            competition.setCompetitors(competitorsEmails);
            for (var i = 0; i < eventNames.size(); i++) {
                Event event = createAndSaveEvent(eventNames.get(i), competition.getStartTime(), i);
                competition.addEvent(event.getId());
            }
            competitionRepository.save(competition);
        }


    }

    private Event createAndSaveEvent(String name, LocalDateTime competitionStartTime, int eventNumber) {
        Event event = new Event(name);
        event.setCompetitorsEmail(allPersons.stream().map(Person::getEmail).collect(Collectors.toSet()));
        event.setStartTime(competitionStartTime.plusMinutes(eventNumber * 10L));

        int numbersOfHeat = fake.random().nextInt(1, 3);
        for (int i = 0; i < numbersOfHeat; i++) {
            Heat heat = new Heat(name, event.getStartTime().plusMinutes(i * 2L));
            List<String> competitorsEmail = allPersons.subList(i, i + 8).stream().map(Person::getEmail).toList();
            heat.setCompetitors(competitorsEmail);

            Map<String, String> results = new HashMap<>();
            competitorsEmail.forEach(email -> results.put(email, fake.random().nextInt(0, 230).toString()));
            heat.setResults(results);

            heat = heatRepository.save(heat);
            event.addHeat(heat.getId());
        }

        return eventRepository.save(event);

    }

    @Override
    public void resetDb() {
        eventRepository.deleteAll();
        heatRepository.deleteAll();
        competitionRepository.deleteAll();
    }
}
