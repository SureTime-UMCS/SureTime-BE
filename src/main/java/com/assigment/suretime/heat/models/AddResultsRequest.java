package com.assigment.suretime.heat.models;


import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddResultsRequest {

    String heatId;
    Map<String, Float> results;
}
