package com.assigment.suretime.person;

import netscape.javascript.JSObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;

import java.net.MalformedURLException;
import java.net.URL;

import static com.assigment.suretime.util.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class PersonControllerTest {


    @Autowired
    private MockMvc mockMvc;
    private URL url = new URL("http", "localhost", 8080, "/api/v1");
    @Autowired
    private PersonService personService;
    PersonControllerTest() throws MalformedURLException {
    }

    @Test
    void all() throws Exception {
        mockMvc.perform(get(url + "/persons"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void one() {
    }

    @Test
    void addOne() throws Exception {
        //GIVEN
        Person person = new Person("szymonzywko@gmail.com");
        personService.addOne(person);


        //WHEN
        mockMvc.perform(post(String.valueOf(new URL(url, "persons")))
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.SEE_OTHER.value()));
    }

    @Test
    void updateCoach() {
    }
}