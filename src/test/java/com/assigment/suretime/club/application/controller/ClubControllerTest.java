package com.assigment.suretime.club.application.controller;

import com.assigment.suretime.generics.models.Address;
import com.assigment.suretime.club.domain.Club;
import com.assigment.suretime.club.domain.service.ClubService;
import com.assigment.suretime.person.domain.models.Person;
import com.assigment.suretime.person.domain.repository.PersonRepository;
import com.assigment.suretime.person.domain.service.DomainPersonService;
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
    private DomainPersonService domainPersonService;
    @Autowired
    private PersonRepository personRepository;

    ClubControllerTest() throws MalformedURLException {
    }

//    @Test
//    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
//    void all() throws Exception {
//        mockMvc.perform(get(url + "/clubs"))
//                .andDo(print()).andExpect(status().isOk());
//    }


    @WithUserDetails("admin")
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
        clubService.removeOne(club.getName());

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

//    @WithMockUser()
//    @Test
//    void addOneAsUserIsUnauthorized() throws Exception {
//        //GIVEN
//        Address address = new Address("c", ",", new BigDecimal("0"), new BigDecimal("0"));
//        Club club = new Club(address, "newClub");
//        clubService.removeOne(club.getName());
//        //WHEN
//        mockMvc.perform(post(url.toString() + "/clubs")
//                        .content(asJsonString(club))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                //THEN
//                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
//
//        clubService.removeOne(club.getName());
//
//    }

    @Test
    //@WithMockUser(value = "ADMIN", roles = {"ADMIN"})
    @WithUserDetails("admin")
    void addUserToClub() throws Exception {
        //GIVEN
        Address address = new Address("c", ",", new BigDecimal("0"), new BigDecimal("0"));
        Club club = new Club(address, "club");
        clubService.addOne(club);
        String email = "testuasdlem@gmail.com";
        Person person = new Person(email);
        domainPersonService.updateOrCreate(person);

        //WHEN

        mockMvc.perform(post(url + "/clubs/" + club.getName() + "/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(person.getEmail()))
                //THEN
                .andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
        //AFTER
        clubService.removeOne(club.getName());
        domainPersonService.removeOne(person.getEmail());
    }

//    @Test
//    //@WithMockUser(value = "ADMIN", roles = {"ADMIN"})
//    @WithUserDetails("admin")
//    void addModeratorsToClubAsAdmin() throws Exception {
//        //GIVEN
//        String clubName = "NotExistingClub";
//        Club newClub = new Club(new Address(), clubName);
//        clubService.removeOne(clubName);
//        clubService.addOne(newClub);
//
//        String newMod = "somebodywhosisgonabemoderator";
//        Person person = new Person(newMod);
//        personService.updateOrCreate(person);
//        AddPersonsToClubModeratorModel moderatorModel =
//                new AddPersonsToClubModeratorModel(List.of(newMod));
//        String payload = asJsonString(moderatorModel);
//        //When
//        mockMvc.perform(put(url + "/clubs/" + clubName + "/add_moderators")
//                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
//                .content(payload)).andDo(print())
//                //Then
//                .andExpect(status().isOk());
//        assert clubService.clubRepository
//                .findByName(newClub.getName())
//                .get()
//                .getClubModerators().stream()
//                .anyMatch(p -> p.getEmail().equals(newMod));
//
//        //After
//        clubService.removeOne(clubName);
//        personService.removeOne(person.getEmail());
//    }
//
//    @Test
//    //@WithMockUser(value = "ADMIN", roles = {"ADMIN"})
//    @WithUserDetails("user")
//    void addModeratorsToClubAsClubModerator() throws Exception {
//        //GIVEN
//        String newMod = "somebodywhosisgonabemoderator@gmail.com";
//        Person person = new Person(newMod);
//        personService.updateOrCreate(person);
//        //prepare club.
//        String clubName = "NotExistingClub";
//        Club club = new Club(new Address(), clubName);
//        Person oldModerator = personRepository.findByEmail("user@gmail.com").get();
//        club.setClubModerators(Set.of(oldModerator));
//
//        //assert club do not exist already
//        clubService.removeOne(clubName);
//        //add clean club with moderator.
//        clubService.addOne(club);
//
//
//        AddPersonsToClubModeratorModel moderatorModel =
//                new AddPersonsToClubModeratorModel(List.of(newMod));
//        String payload = asJsonString(moderatorModel);
//        //When
//        mockMvc.perform(put(url + "/clubs/" + clubName + "/add_moderators")
//                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
//                        .content(payload)).andDo(print())
//                //Then
//                .andExpect(status().isOk());
//        assert clubService.clubRepository
//                .findByName(club.getName())
//                .get()
//                .getClubModerators().stream()
//                .anyMatch(p -> p.getEmail().equals(newMod));
//
//        //After
//        clubService.removeOne(clubName);
//        personService.removeOne(person.getEmail());
//    }

}