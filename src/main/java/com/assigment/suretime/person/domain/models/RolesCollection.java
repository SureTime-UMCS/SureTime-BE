package com.assigment.suretime.person.domain.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

@Getter
@Setter
public class RolesCollection {

    @PersistenceConstructor
    public RolesCollection(List<String> roles) {
        this.roles = roles;
    }
    public RolesCollection(){};

    private List<String> roles;
}
