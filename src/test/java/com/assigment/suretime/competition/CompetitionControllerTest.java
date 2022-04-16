package com.assigment.suretime.competition;

import com.assigment.suretime.competition.models.Competition;
import com.assigment.suretime.competition.models.CompetitionDto;
import com.assigment.suretime.event.Event;
import com.assigment.suretime.event.EventRepository;
import com.assigment.suretime.heat.HeatRepository;
import com.assigment.suretime.person.PersonRepository;
import com.assigment.suretime.person.models.Person;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import netscape.javascript.JSObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

import static com.assigment.suretime.util.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class CompetitionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private HeatRepository heatRepository;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private EventRepository eventRepository;
    private URL url;

    private final Faker fake = new Faker(new Locale("pl_PL"));

    {
        try {
            url = new URL("http", "localhost", 8080, "/api/v1/competitions");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Before()
    public void setup() {
        //Init MockMvc Object and build
        assert heatRepository.findAll().size() > 10 : "Insert More heats";
        assert personRepository.findAll().size() > 10 : "Insert More people";
        assert competitionRepository.findAll().size() > 5 : "Insert more competition";
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @SneakyThrows
    @WithUserDetails("user")
    void one() {
        Competition competition = getRandomCompetition();
        mockMvc.perform(get(url + "/" + competition.getId()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void updateOne() {
        Competition randomCompetition = getRandomCompetition();

        CompetitionDto competitionDto = randomCompetition.toDto();
        String newName = "Nairobi Open";
        competitionDto.setName(newName);
        String payload = asJsonString(competitionDto);
        mockMvc.perform(put(url +"/"+ randomCompetition.getId())
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        assert competitionRepository.findById(randomCompetition.getId()).isPresent();
        Optional<Competition> byName = competitionRepository.findByName(newName);
        assert byName.isPresent();
        assert byName.get().getName().equals(newName);
        assert randomCompetition.getEventsId().containsAll(byName.get().getEventsId());


    }

    @Test
    @SneakyThrows
    @WithUserDetails("user")
    void all() {
        mockMvc.perform(get(url.toURI()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void deleteOne() {
        Competition randCompetition = getRandomCompetition();

        mockMvc.perform(delete(url + "/" + randCompetition.getId()))
                .andExpect(status().is2xxSuccessful());

        assert competitionRepository.findById(randCompetition.getId()).isEmpty();

    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void addCompetitor() {
        Person randPerson = getRandomPerson();
        Competition randCompetition = getRandomCompetition();
        randCompetition.getCompetitors().remove(randPerson.getEmail());
        Competition updated = competitionRepository.save(randCompetition);
        String payload = asJsonString(updated.toDto());

        mockMvc.perform(post(url + "/" + randCompetition.getId() +
                        "/add_competitor/" + randPerson.getEmail())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(payload))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        assert competitionRepository.findById(randCompetition.getId())
                .get().getCompetitors()
                .contains(randPerson.getEmail());

    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void removeCompetitor() {
        Person randPerson = getRandomPerson();
        Competition randCompetition = getRandomCompetition();
        randCompetition.getCompetitors().add(randPerson.getEmail());
        Competition updated = competitionRepository.save(randCompetition);
        String payload = asJsonString(updated.toDto());

        mockMvc.perform(delete(url + "/" + randCompetition.getId() +
                        "/remove_competitor/" + randPerson.getEmail())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(payload))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        assert !competitionRepository.findById(randCompetition.getId())
                .get().getCompetitors()
                .contains(randPerson.getEmail());

    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void addEvent() {
        Event randEvent = getRandomEvent();
        Competition randCompetition = getRandomCompetition();
        randCompetition.getCompetitors().remove(randEvent.getId());
        Competition updated = competitionRepository.save(randCompetition);
        String payload = asJsonString(updated.toDto());

        mockMvc.perform(post(url + "/" + randCompetition.getId() +
                        "/add_event/" + randEvent.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(payload))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        assert competitionRepository.findById(randCompetition.getId())
                .get().getEventsId()
                .contains(randEvent.getId());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void removeEvent() {
        Event randEvent = getRandomEvent();
        Competition randCompetition = getRandomCompetition();
        randCompetition.getCompetitors().add(randEvent.getId());
        Competition updated = competitionRepository.save(randCompetition);
        String payload = asJsonString(updated.toDto());

        mockMvc.perform(delete(url + "/" + randCompetition.getId() +
                        "/remove_event/" + randEvent.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(payload))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        assert !competitionRepository.findById(randCompetition.getId())
                .get().getEventsId()
                .contains(randEvent.getId());
    }

    private Competition getRandomCompetition() {
        int randIndex = fake.random().nextInt(0, 4);
        return competitionRepository.findAll().get(randIndex);
    }

    private Person getRandomPerson() {
        int randIndex = fake.random().nextInt(0, 10);
        return personRepository.findAll().get(randIndex);
    }

    private Event getRandomEvent() {
        int randIndex = fake.random().nextInt(0, 10);
        return eventRepository.findAll().get(randIndex);


    }
}