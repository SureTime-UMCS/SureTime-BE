package com.assigment.suretime.person;

import com.assigment.suretime.person.Person;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;

import static com.assigment.suretime.util.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class PersonControllerTest {



    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;



    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    private URL url = new URL("http", "localhost", 8080, "/api/v1");

    @Autowired
    private PersonService personService;

    PersonControllerTest() throws MalformedURLException {
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
    void all() throws Exception {
        mockMvc.perform(get(url + "/persons"))
                .andDo(print()).andExpect(status().isOk());
    }


    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
    @Test
    void removeOneAsAdminIsOk() throws Exception {
        //GIVEN
        Person person = new Person("szymonzywko@gmail.com");
        personService.addOne(person);
        //WHEN
        mockMvc.perform(delete(url.toString() + "/persons/" + person.getEmail()))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.OK.value()));
        personService.removeOne(person.getEmail());
    }

    @WithMockUser()
    @Test
    void removeOneAsUserIsForbidden() throws Exception {
        //GIVEN
        Person person = new Person("szymonzywko@gmail.com");
        personService.addOne(person);
        //WHEN
        mockMvc.perform(delete(url.toString() + "/persons/" + person.getEmail()))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        personService.removeOne(person.getEmail());
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
    void addOneAsAdminIsOk() throws Exception {
        //GIVEN
        Person person = new Person("testuser@gmial.com");
        personService.removeOne(person.getEmail());

        //WHEN
        mockMvc.perform(post(url.toString() + "/persons")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.CREATED.value()));
        personService.removeOne(person.getEmail());
    }

    @WithMockUser()
    @Test
    void addOneAsUserIsUnauthorized() throws Exception {
        //GIVEN
        Person person = new Person("testuser@gmial.com");
        personService.addOne(person);
        //WHEN
        mockMvc.perform(post(url.toString() + "/persons")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

        personService.removeOne(person.getEmail());
    }

}