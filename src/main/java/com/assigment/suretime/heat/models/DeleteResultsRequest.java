package com.assigment.suretime.heat.models;


import lombok.*;
import org.springframework.data.util.Pair;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResultsRequest {

    String heatId;
    List<Pair<String, String>> results;
}
