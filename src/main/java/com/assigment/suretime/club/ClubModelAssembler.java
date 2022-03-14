package com.assigment.suretime.club;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ClubModelAssembler implements RepresentationModelAssembler<Club, EntityModel<Club>>{
    @Override
    public EntityModel<Club> toModel(Club club) {
        return EntityModel.of(club,
                linkTo(methodOn(ClubController.class).getClubByName(club.getName())).withSelfRel(),
                linkTo(methodOn(ClubController.class).getAllClubs()).withRel("clubs"));
    }
}

