package com.assigment.suretime.club.application.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AddPersonsToClubModel {
    private List<String> personUUID;

    @PersistenceConstructor
    public AddPersonsToClubModel(List<String> personUUID) {
        this.personUUID = personUUID;
    }
}
