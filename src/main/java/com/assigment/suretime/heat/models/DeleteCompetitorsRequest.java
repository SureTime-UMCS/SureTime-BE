package com.assigment.suretime.heat.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCompetitorsRequest {

    String heatId;
    List<String> competitorsEmails;
}
