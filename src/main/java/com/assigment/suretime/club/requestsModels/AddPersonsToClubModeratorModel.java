package com.assigment.suretime.club.requestsModels;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AddPersonsToClubModeratorModel {
    private List<String> personEmails;

    @PersistenceConstructor
    public AddPersonsToClubModeratorModel(List<String> personEmails) {
        this.personEmails = personEmails;
    }
}
