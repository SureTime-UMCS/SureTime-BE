package com.assigment.suretime.heat.models;


import lombok.*;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddResultsRequest {

    String heatId;
    List<Pair<String, String>> results;
}
