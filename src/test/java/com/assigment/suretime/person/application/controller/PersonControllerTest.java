package com.assigment.suretime.person.application.controller;

import com.assigment.suretime.person.application.response.PersonDTO;
import com.assigment.suretime.person.domain.models.Gender;
import com.assigment.suretime.person.domain.models.Person;
import com.assigment.suretime.person.domain.models.RolesCollection;
import com.assigment.suretime.person.domain.repository.PersonRepository;
import com.assigment.suretime.person.domain.service.PersonService;
import com.assigment.suretime.securityJwt.domain.models.ERole;
import com.assigment.suretime.securityJwt.domain.models.User;
import com.assigment.suretime.securityJwt.domain.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PersonRepository personRepository;



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

//    @Test
//    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
//    void all() throws Exception {
//        mockMvc.perform(get(url + "/persons"))
//                .andDo(print()).andExpect(status().isOk());
//    }


    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
    @Test
    void removeOneAsAdminIsOk() throws Exception {
        //GIVEN
        Person person = new Person("szymonzywko@gmail.com");
        personService.updateOrCreate(person);
        //WHEN
        mockMvc.perform(delete(url.toString() + "/persons/" + person.getEmail()))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.OK.value()));

        assert personRepository.findByEmail(person.getEmail()).isEmpty();
        personService.removeOne(person.getEmail());
    }

    @WithMockUser()
    @Test
    void removeOneAsUserIsForbidden() throws Exception {
        //GIVEN
        Person person = new Person("szymonzywko@gmail.com");
        personRepository.findByEmail(person.getEmail())
                .ifPresentOrElse(person1 -> {}, ()->{
                    personRepository.insert(person);
                });


        //WHEN
        mockMvc.perform(delete(url.toString() + "/persons/" + person.getEmail()))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));


        Optional<Person> byEmail = personRepository.findByEmail(person.getEmail());
        assert byEmail.isPresent();
        personService.removeOne(person.getEmail());
    }

    @Test
    @WithMockUser(value = "admin", roles = "ADMIN")
    void addOneAsAdminIsOk() throws Exception {
        //GIVEN
        PersonDTO personDTO = new PersonDTO("Szymon", "Zywko", "zywko@gmail.com", Gender.MALE, "AZS", "admin@gmail.com");
        personService.removeOne(personDTO.getEmail());

        //WHEN
        mockMvc.perform(post(url.toString() + "/persons")
                        .content(asJsonString(personDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.CREATED.value()));
        Person p =personRepository.findByEmail(personDTO.getEmail()).get();
        assert personRepository.findByEmail(personDTO.getEmail()).isPresent();
        personService.removeOne(personDTO.getEmail());
    }

    @Test
    @WithUserDetails(value = "mod")
    void getMyData() throws Exception {
        Person person = new Person("mod@gmail.com");
        ResponseEntity<?> entityModelResponseEntity = personService.updateOrCreate(person);

        //WHEN
        mockMvc.perform(get(url.toString() + "/persons/me"))
                .andDo(print())
                //THEN
                .andExpect(status().is(HttpStatus.OK.value()));

        assert personRepository.findByEmail(person.getEmail()).isPresent();
        personService.removeOne(person.getEmail());
    }


    @WithMockUser()
    @Test
    void addOneAsUserIsUnauthorized() throws Exception {
        //GIVEN
        Person person = new Person("testuser@gmial.com");

        personService.updateOrCreate(person);
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

    @Test
    @WithMockUser(value = "admin", roles = "ADMIN")
    void updateRolesSuccess() throws Exception {
        //this user is always inserted during seeding.
        String email = "pzla@gmail.com";
        User user = userRepository.findByEmail(email).get();
        var oldRoles = user.getRoles();
        RolesCollection rolesCollection = new RolesCollection(List.of("ROLE_ADMIN"));
        String payload = asJsonString(rolesCollection);
        mockMvc.perform(put(url.toString()+"/persons/roles/"+ email)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print()).andExpect(status().isOk());

        user = userRepository.findByEmail(email).get();
        var newRoles = user.getRoles();
        assert newRoles.stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN));
    }

}