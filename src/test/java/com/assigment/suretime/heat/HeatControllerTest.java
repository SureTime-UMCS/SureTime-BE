package com.assigment.suretime.heat;

import com.assigment.suretime.heat.models.Heat;
import com.assigment.suretime.heat.models.HeatDto;
import com.assigment.suretime.person.PersonRepository;
import netscape.javascript.JSObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.assigment.suretime.address.Address;
import com.assigment.suretime.club.requestsModels.AddPersonsToClubModeratorModel;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.person.PersonRepository;
import com.assigment.suretime.person.PersonService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.assigment.suretime.util.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


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
        assert personRepository.findAll().size() > 10: "Insert More people";
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

    private String getRandomHeatId(){
        int randomIndex = new Random().nextInt(0,10);
        return heatRepository.findAll().get(randomIndex).getId();
    }

    private Heat getRandomHeat(){
        int randomIndex = new Random().nextInt(0,10);
        return heatRepository.findAll().get(randomIndex);
    }
    @Test
    @WithUserDetails("user")
    void oneSuccess() throws Exception {
        String heatId = getRandomHeatId();
        mockMvc.perform(get(url + "/"+heatId)).andDo(print()).andExpect(status().is2xxSuccessful());
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

        mockMvc.perform(put(url.toString())
                        .content(payload)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        var updated = heatRepository.findById(heat.getId());

        assert heatRepository.findById(heat.getId()).get().getName().equals(newName);
//        assert heatRepository.findById(heat.getId()).get().getStartTime().equals(newStartTime);



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
        mockMvc.perform(delete(url + "/"+id)).andDo(print()).andExpect(status().is2xxSuccessful());

        assert heatRepository.findById(id).isEmpty();
    }

    @Test
    void addCompetitor() {
    }

    @Test
    void addResults() {
    }

    @Test
    void updateName() {
    }
}