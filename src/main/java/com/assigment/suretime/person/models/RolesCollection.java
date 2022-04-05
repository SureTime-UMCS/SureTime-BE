package com.assigment.suretime.person.models;

import com.assigment.suretime.securityJwt.models.ERole;
import lombok.AllArgsConstructor;
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
