package com.assigment.suretime.event.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EventDTO {
    String name;
    LocalDateTime startTime;
    java.util.Set<String> competitorsUuid;
    Set<String> heatsId;
}
