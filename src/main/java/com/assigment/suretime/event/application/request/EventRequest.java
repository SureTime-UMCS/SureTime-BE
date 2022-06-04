package com.assigment.suretime.event.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EventRequest {
    String name;
    LocalDateTime startTime;
}
