package com.assigment.suretime.heat;

import com.assigment.suretime.heat.application.request.AddCompetitorsRequest;
import com.assigment.suretime.heat.application.request.AddResultsRequest;
import com.assigment.suretime.heat.application.request.DeleteCompetitorsRequest;
import com.assigment.suretime.heat.application.request.DeleteResultsRequest;
import com.assigment.suretime.heat.application.response.HeatDto;
import com.assigment.suretime.heat.domain.Heat;
import com.assigment.suretime.heat.domain.repository.HeatRepository;
import com.assigment.suretime.person.domain.repository.PersonRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.assigment.suretime.person.domain.models.Person;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.assigment.suretime.util.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.net.MalformedURLException;
import java.net.URL;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class HeatControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private HeatRepository heatRepository;


    @Before()
    public void setup() {
        //Init MockMvc Object and build
        assert heatRepository.findAll().size() > 10 : "Insert More heats";
        assert personRepository.findAll().size() > 10 : "Insert More people";
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    private URL url;

    {
        try {
            url = new URL("http", "localhost", 8080, "/api/v1/heats");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private String getRandomHeatId() {
        int randomIndex = new Random().nextInt(0, 10);
        return heatRepository.findAll().get(randomIndex).getId();
    }

    private Heat getRandomHeat() {
        int randomIndex = new Random().nextInt(0, 10);
        return heatRepository.findAll().get(randomIndex);
    }

    private Person getRandomPerson() {
        int randomIndex = new Random().nextInt(0, 20);
        return personRepository.findAll().get(randomIndex);
    }

    @Test
    @WithUserDetails("user")
    void oneSuccess() throws Exception {
        String heatId = getRandomHeatId();
        mockMvc.perform(get(url + "/" + heatId)).andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    void updateOne() throws Exception {
        Heat heat = getRandomHeat();
        HeatDto dto = heat.toDto();
        String newName = "NAMEFAEm";
        dto.setName(newName);
        LocalDateTime newStartTime = LocalDateTime.now();
        dto.setStartTime(newStartTime);
        String payload = asJsonString(dto);

        mockMvc.perform(put(url+"/"+heat.getId())
                        .content(payload)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        assert heatRepository.findById(heat.getId()).get().getName().equals(newName);
        assert heatRepository.findById(heat.getId()).get().getStartTime()
                .getSecond() == newStartTime.getSecond();


    }

    @Test
    @WithUserDetails("user")
    void all() throws Exception {
        mockMvc.perform(get(url.toURI())).andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    void deleteOne() throws Exception {
        String id = getRandomHeatId();
        mockMvc.perform(delete(url + "/" + id)).andDo(print()).andExpect(status().is2xxSuccessful());

        assert heatRepository.findById(id).isEmpty();
    }

    @SneakyThrows
    @Test
    @WithUserDetails("admin")
    void addCompetitors() {
        Heat heat = getRandomHeat();
        List<String> competitorsUUIDs = List.of(getRandomPerson().getUserUUID(), getRandomPerson().getEmail());
        String payload = asJsonString(new AddCompetitorsRequest(competitorsUUIDs));

        mockMvc.perform(post(url + "/"+heat.getId()+"/competitors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());


        List<String> competitors = heatRepository.findById(heat.getId()).get().getCompetitors();
        competitorsUUIDs.forEach(s ->
                {
                    assert competitors.stream().anyMatch(person -> person.equals(s));
                }
        );
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void addResults() {

        Person randomPerson = getRandomPerson();
        Person randomPerson2 = getRandomPerson();

        String result = String.valueOf(209.0);
        String result2 =String.valueOf(210.0);

        Heat randomHeat = getRandomHeat();

        List<Pair<String, String>> results = List.of(
                Pair.of(randomPerson.getEmail(), result),
                Pair.of(randomPerson2.getEmail(), result2)
        );

        AddResultsRequest payloadObject = new AddResultsRequest(results);

        String payload = asJsonString(
                payloadObject);
        mockMvc.perform(post(url+"/"+randomHeat.getId()+"/results")
                .accept(MediaType.APPLICATION_JSON)
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        Heat updatedHeat = heatRepository.findById(randomHeat.getId()).get();
        Map<String, String> updatedHeatResults = updatedHeat.getResults();
        assert updatedHeatResults.get(randomPerson.getEmail()).equals(result);
        assert updatedHeatResults.get(randomPerson2.getEmail()).equals(result2);
        assert !updatedHeatResults.get(randomPerson.getEmail()).equals(result2);

    }

    @Test
    @WithUserDetails("admin")
    @SneakyThrows
    void deleteCompetitor() throws Exception {
        Heat randomHeat = getRandomHeat();
        List<String> heatCompetitors = randomHeat.getCompetitors();
        String email1 = heatCompetitors.get(new Random().nextInt(0, heatCompetitors.size()));
        String email2 = heatCompetitors.get(new Random().nextInt(0, heatCompetitors.size()));

        DeleteCompetitorsRequest request = new DeleteCompetitorsRequest(List.of(email1, email2));

        String payload = asJsonString(
                request);
        mockMvc.perform(delete(url+"/"+randomHeat.getId()+"/competitors")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
        Heat updated = heatRepository.findById(randomHeat.getId()).get();
        assert !updated.getCompetitors().contains(email1);
        assert !updated.getCompetitors().contains(email2);

    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void updateHeatTEst() {
        Heat heat = getRandomHeat();
        HeatDto dto = heat.toDto();
        String newName = "Memorial czasu poswieconego temu projektowi.";
        dto.setName(newName);
        dto.setResults(null);
        dto.setCompetitorsUUID(null);

        String payload = asJsonString(dto);

        mockMvc.perform(put(url + "/"+heat.getId())
                        .content(payload)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        Heat afterUpdate = heatRepository.findById(heat.getId()).get();
        assert afterUpdate.getCompetitors() != null;
        assert afterUpdate.getResults() != null;
        assert afterUpdate.getName().equals(newName);
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void deleteResults() {
        Person randomPerson = getRandomPerson();
        Person randomPerson2 = getRandomPerson();

        String result = String.valueOf(209.0);
        String result2 =String.valueOf(210.0);

        Heat randomHeat = getRandomHeat();

        List<Pair<String, String>> results = List.of(
                Pair.of(randomPerson.getEmail(), result),
                Pair.of(randomPerson2.getEmail(), result2)
        );
        randomHeat.getResults().put(randomPerson.getEmail(), result);
        randomHeat.getResults().put(randomPerson2.getEmail(), result2);

        DeleteResultsRequest payloadObject = new DeleteResultsRequest(results);

        String payload = asJsonString(
                payloadObject);
        mockMvc.perform(delete(url+"/"+randomHeat.getId()+"/results")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        Heat updatedHeat = heatRepository.findById(randomHeat.getId()).get();
        Map<String, String> updatedHeatResults = updatedHeat.getResults();
        assert !updatedHeatResults.containsKey(randomPerson.getEmail());
        assert !updatedHeatResults.containsKey(randomPerson2.getEmail());
    }
}