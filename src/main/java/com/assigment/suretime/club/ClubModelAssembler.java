package com.assigment.suretime.club;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class ClubModelAssembler implements RepresentationModelAssembler<Club, EntityModel<Club>> {
    @Override
    public EntityModel<Club> toModel(Club club) {
        return EntityModel.of(club,
                linkTo(methodOn(ClubController.class).getOne(club.getName())).withSelfRel(),
                linkTo(methodOn(ClubController.class).all()).withRel("clubs"));
    }

    @Override
    public CollectionModel<EntityModel<Club>> toCollectionModel(Iterable<? extends Club> entities) {
        List<EntityModel<Club>> clubs = StreamSupport.stream(entities.spliterator(), false).map(this::toModel).toList();
        return CollectionModel.of(clubs,
                linkTo(methodOn(ClubController.class).all()).withSelfRel());
    }
}

