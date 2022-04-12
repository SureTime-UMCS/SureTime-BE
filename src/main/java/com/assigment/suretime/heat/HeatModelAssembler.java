package com.assigment.suretime.heat;


import com.assigment.suretime.person.PersonController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class is used for generating links each Person object.
 */
@Component
public class HeatModelAssembler implements RepresentationModelAssembler<Heat, EntityModel<Heat>> {

    @Override
    public EntityModel<Heat> toModel(Heat heat){
        return EntityModel.of(heat,
                linkTo(methodOn(HeatController.class).one(heat.getId())).withSelfRel(),
                linkTo(methodOn(HeatController.class).all()).withRel("heats"));
    }

    @Override
    public CollectionModel<EntityModel<Heat>> toCollectionModel(Iterable<? extends Heat> collection) {
        var entities = StreamSupport.stream(collection.spliterator(), false).map(this::toModel).toList();
        return CollectionModel.of(entities,
                linkTo(methodOn(HeatController.class).all()).withSelfRel());
    }
}
