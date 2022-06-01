package com.assigment.suretime.heat.application.request;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.server.ServerEndpoint;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddCompetitorsRequest {

    String heatId;
    List<String> competitorsEmails;
}
