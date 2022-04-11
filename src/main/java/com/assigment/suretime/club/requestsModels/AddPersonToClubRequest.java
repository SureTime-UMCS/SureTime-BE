package com.assigment.suretime.club.requestsModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPersonToClubRequest {
    private String clubName;
    private String personEmail;
}
