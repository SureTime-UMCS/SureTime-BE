package com.assigment.suretime.club;

import com.assigment.suretime.person.Person;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/clubs")
public class ClubController {

    private final ClubService service;


    @GetMapping("/")
    public CollectionModel<EntityModel<Club>> all(){
        List<EntityModel<Club>> clubs = service.getAll();
        return CollectionModel.of(clubs,
                linkTo(methodOn(ClubController.class).all()).withSelfRel());
    }

    @GetMapping("/{name}")
    public EntityModel<Club> getOne(@PathVariable String name){
        return service.getByName(name);
    }

    @PostMapping()
    EntityModel<Club> addOne(@RequestBody Club club){
        return service.addOne(club);
    }

    @PutMapping("/{name}")
    EntityModel<Club> updateOne(@RequestBody Club newClub, @PathVariable String name){
        EntityModel<Club> entityModel = service.updateOne(newClub, name);
        return entityModel;

    }

    @DeleteMapping("/{name}")
    void deleteClub(@PathVariable String name){
        service.deleteByName(name);
    }

    @PostMapping("addToClub/")
    EntityModel<Club> addPersonToClub(@RequestBody String clubName,  @RequestBody String personEmail){
        return service.addPersonToClub(clubName, personEmail);
    }

}
