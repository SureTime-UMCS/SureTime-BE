package com.assigment.suretime.heat.application.request;


import lombok.*;
import org.springframework.data.util.Pair;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResultsRequest {
    List<Pair<String, String>> results;
}
