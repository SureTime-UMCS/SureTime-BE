package com.assigment.suretime.club;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPersonToClubRequest {
    private String clubName;
    private String personEmail;
}
