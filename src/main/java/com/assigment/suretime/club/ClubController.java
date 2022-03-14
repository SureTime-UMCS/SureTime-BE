package com.assigment.suretime.club;

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

    private final ClubService clubService;


    @GetMapping("/")
    public CollectionModel<EntityModel<Club>> getAllClubs(){
        List<EntityModel<Club>> clubs = clubService.getAllClubs();
        return CollectionModel.of(clubs,
                linkTo(methodOn(ClubController.class).getAllClubs()).withSelfRel());
    }

    @GetMapping("/{name}")
    public EntityModel<Club> getClubByName(@PathVariable String name){
        return clubService.getClubByName(name);
    }

    @PostMapping()
    EntityModel<Club> addClub(@RequestBody Club club){
        return clubService.addClub(club);
    }

    @PutMapping("/{name}")
    EntityModel<Club> replaceClub(@RequestBody Club newClub, @PathVariable String name){
        return clubService.replaceClub(newClub, name);
    }

    @DeleteMapping("/{name}")
    void deleteClub(@PathVariable String name){
        clubService.deleteByName(name);
    }

}
