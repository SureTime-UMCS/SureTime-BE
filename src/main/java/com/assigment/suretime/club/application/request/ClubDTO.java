package com.assigment.suretime.club.application.request;

import com.assigment.suretime.generics.models.Address;
import com.assigment.suretime.person.domain.models.Gender;
import com.assigment.suretime.person.domain.models.Person;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Set;

@Data
@NoArgsConstructor
public class ClubDTO {

    private Address address;
    private String name;
    private Set<String> members, clubModerators;

    @PersistenceConstructor
    @Builder
    public ClubDTO(Address address, String name, Set<String> members, Set<String> clubModerators) {
        this.address = address;
        this.name = name;
        this.members = members;
        this.clubModerators = clubModerators;
    }
}
