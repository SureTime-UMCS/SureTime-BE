package com.assigment.suretime.club;

import com.assigment.suretime.person.Person;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/clubs")
public class ClubController {

    private final ClubService service;

    @GetMapping("/")
    public CollectionModel<EntityModel<Club>> all(){
        return service.getAll();
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
