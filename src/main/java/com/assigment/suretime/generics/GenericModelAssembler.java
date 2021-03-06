package com.assigment.suretime.generics;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;
import java.util.Locale;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class GenericModelAssembler<T extends MongoModel> implements RepresentationModelAssembler<T, EntityModel<T>> {

    Class<? extends IGenericController> tControllerClass;
    Class<T> tClass;

    public GenericModelAssembler(Class<T> tClass, Class<? extends IGenericController> tControllerClass) {
        this.tControllerClass = tControllerClass;
        this.tClass = tClass;
    }

    @Override
    public EntityModel<T> toModel(T t){
        String className = (tClass.getSimpleName()+"s").toLowerCase(Locale.ROOT);
        return EntityModel.of(t,
                linkTo(methodOn(tControllerClass).one(t.getId())).withSelfRel(),
                linkTo(methodOn(tControllerClass).all()).withRel(className));
    }

    @Override
    public CollectionModel<EntityModel<T>> toCollectionModel(Iterable<? extends T> collection) {
        List<EntityModel<T>> entities = StreamSupport.stream(collection.spliterator(), false)
                .map(this::toModel).toList();
        return CollectionModel.of(entities,
                linkTo(methodOn(tControllerClass).all()).withSelfRel());
    }

}