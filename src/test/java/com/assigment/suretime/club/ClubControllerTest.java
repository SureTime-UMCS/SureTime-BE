package com.assigment.suretime.club;

import com.assigment.suretime.address.Address;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static com.assigment.suretime.util.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class ClubControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;


    @Before()
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    private URL url = new URL("http", "localhost", 8080, "/api/v1");

    @Autowired
    private ClubService clubService;
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    ClubControllerTest() throws MalformedURLException {
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
    void all() throws Exception {
        mockMvc.perform(get(url + "/clubs"))
                .andDo(print()).andExpect(status().isOk());
    }


    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
    @Test
    void removeOneAsAdminIsOk() throws Exception {
        //GIVEN
        Address address = new Address("c", ",", new BigDecimal("0"), new BigDecimal("0"));
        Club club = new Club(address, "newClub");
        clubService.addOne(club);
        //WHEN
        mockMvc.perform(delete(url.toString() + "/clubs/" + club.getName()))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.OK.value()));
        clubService.removeOne(club.getName());
    }

    @WithMockUser()
    @Test
    void removeOneAsUserIsForbidden() throws Exception {
        //GIVEN
        Address address = new Address("c", ",", new BigDecimal("0"), new BigDecimal("0"));
        Club club = new Club(address, "newClub");
        //WHEN
        mockMvc.perform(delete(url.toString() + "/clubs/" + club.getName()))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        clubService.removeOne(club.getName());

    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
    void addOneAsAdminIsOk() throws Exception {
        //GIVEN
        Address address = new Address("c", ",", new BigDecimal("0"), new BigDecimal("0"));
        Club club = new Club(address, "newClub");

        //WHEN
        mockMvc.perform(post(url.toString() + "/clubs")
                        .content(asJsonString(club))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.CREATED.value()));
        clubService.removeOne(club.getName());

    }

    @WithMockUser()
    @Test
    void addOneAsUserIsUnauthorized() throws Exception {
        //GIVEN
        Address address = new Address("c", ",", new BigDecimal("0"), new BigDecimal("0"));
        Club club = new Club(address, "newClub");
        //WHEN
        mockMvc.perform(post(url.toString() + "/clubs")
                        .content(asJsonString(club))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

        clubService.removeOne(club.getName());

    }

    @Test
    @WithMockUser(value = "ADMIN", roles = {"ADMIN"})
    void addUserToClub() throws Exception {
        //GIVEN
        Address address = new Address("c", ",", new BigDecimal("0"), new BigDecimal("0"));
        Club club = new Club(address, "newClub");
        clubService.addOne(club);
        String email = "testuasdlem@gmail.com";
        Person person = new Person(email);
        personService.updateOrCreate(person);

        //WHEN

        mockMvc.perform(put(url + "/clubs/" + club.getName() + "/add_person_to_club")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(person.getEmail()))
                .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
        //AFTER
        clubService.removeOne(club.getName());
        personService.removeOne(person.getEmail());
    }

}