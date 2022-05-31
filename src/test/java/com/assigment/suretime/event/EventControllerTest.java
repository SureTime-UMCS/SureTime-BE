package com.assigment.suretime.event;

import com.assigment.suretime.event.application.response.EventDto;
import com.assigment.suretime.event.domain.Event;
import com.assigment.suretime.event.domain.repository.EventRepository;
import com.assigment.suretime.heat.domain.repository.HeatRepository;
import com.assigment.suretime.heat.domain.Heat;
import com.assigment.suretime.person.domain.repository.PersonRepository;
import com.assigment.suretime.person.domain.models.Person;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static com.assigment.suretime.util.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class EventControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private HeatRepository heatRepository;

    @Autowired
    private EventRepository eventRepository;

    private final Faker fake = new Faker();


    private URL url;
    {
        try {
            url = new URL("http", "localhost", 8080, "/api/v1/events");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Before()
    public void setup() {
        //Init MockMvc Object and build
        assert heatRepository.findAll().size() > 10 : "Insert more heats";
        assert personRepository.findAll().size() > 10 : "Insert more people";
        assert eventRepository.findAll().size() > 10 : "Insert more events";
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    private Heat getRandomHeat() {
        int randIndex = fake.random().nextInt(0, 10);
        return heatRepository.findAll().get(randIndex);
    }

    private Person getRandomPerson() {
        int randIndex = fake.random().nextInt(0, 10);
        return personRepository.findAll().get(randIndex);
    }

    private Event getRandomEvent(){
        int randIndex = fake.random().nextInt(0, 10);
        return eventRepository.findAll().get(randIndex);
    }

    @Test
    @WithUserDetails
    void one() throws Exception {
        Event randEvent = getRandomEvent();

        mockMvc.perform(get(url+"/"+randEvent.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful());


    }

    @Test
    @SneakyThrows
    @WithUserDetails
    void all() {

        mockMvc.perform(get(url.toURI())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void updateOne() {
        //GIVEN
        Event event = getRandomEvent();
        String oldCompetitor = "oldCompetitos53421@gmail.com";
        event.getCompetitorsEmail().add(oldCompetitor);
        eventRepository.save(event);
        String newName = "Skok przez plot.";
        String newCompetitor = "xdd16575@gmail.com";
        EventDto eventPayload = new EventDto(event);
        eventPayload.getCompetitorsEmail().add(newCompetitor);
        eventPayload.setName(newName);

        String payload = asJsonString(eventPayload);

        //WHEN
        mockMvc.perform(put(url+"/"+event.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());



        Event updated = eventRepository.findById(event.getId()).get();
        assert updated.getName().equals(newName);
        assert updated.getCompetitorsEmail().contains(oldCompetitor);
        assert updated.getCompetitorsEmail().contains(newCompetitor);


    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void deleteOne() {
        Event event = getRandomEvent();

        mockMvc.perform(delete(url+"/"+event.getId()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        assert eventRepository.findById(event.getId()).isEmpty();
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void addHeat() {
        //{id}/add_heat/{heat_id}
        Event randomEvent = getRandomEvent();
        String newHeatName = "New heat";
        Heat newHeat = heatRepository.save(new Heat(newHeatName, LocalDateTime.now()));

        mockMvc.perform(post(url+ "/"+randomEvent.getId() +"/heat/"+ newHeat.getId()))
                .andDo(print()).andExpect(status().is2xxSuccessful());

        eventRepository.findById(randomEvent.getId()).get().getHeatsId().contains(newHeat.getId());
        heatRepository.deleteById(newHeat.getId());
    }

    @Test
    @WithUserDetails("admin")
    @SneakyThrows
    void deleteHeat() {
        //{id}/add_heat/{heat_id}
        Event randomEvent = getRandomEvent();
        String newHeatName = "New heat";
        Heat newHeat = heatRepository.save(new Heat(newHeatName, LocalDateTime.now()));
        randomEvent.getHeatsId().add(newHeat.getId());
        eventRepository.save(randomEvent);

        mockMvc.perform(delete(url+ "/"+randomEvent.getId() +"/heat/"+ newHeat.getId()))
                .andDo(print()).andExpect(status().is2xxSuccessful());

        Event updatedHeat = eventRepository.findById(randomEvent.getId()).get();
        assert !updatedHeat.getHeatsId().contains(newHeat.getId());
        updatedHeat.getHeatsId().remove(newHeat.getId());
        heatRepository.deleteById(newHeat.getId());
        eventRepository.save(updatedHeat);

    }

    @Test
    @WithUserDetails("admin")
    @SneakyThrows
    void deleteCompetitor() {
        //{id}/add_heat/{heat_id}
        Event randomEvent = getRandomEvent();
        Person person = getRandomPerson();
        randomEvent.getCompetitorsEmail().add(person.getEmail());
        eventRepository.save(randomEvent);

        mockMvc.perform(delete(url+"/"+ randomEvent.getId() +"/competitor/"+ person.getId()))
                .andDo(print()).andExpect(status().is2xxSuccessful());

        assert !eventRepository.findById(randomEvent.getId()).get().getCompetitorsEmail().contains(person.getId());

    }

    @Test
    @WithUserDetails("admin")
    @SneakyThrows
    void addCompetitor() {
        //{id}/add_heat/{heat_id}
        Event randomEvent = getRandomEvent();
        Person person = getRandomPerson();

        mockMvc.perform(post(url+ "/"+randomEvent.getId() +"/competitor/"+ person.getId()))
                .andDo(print()).andExpect(status().is2xxSuccessful());

        assert eventRepository.findById(randomEvent.getId()).get().getCompetitorsEmail().contains(person.getId());

    }
}