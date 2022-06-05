package com.assigment.suretime.competition.application.request;

import com.assigment.suretime.generics.models.Address;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CompetitionRequest {
    private String name;
    private Address address;
    private LocalDateTime startTime, endTime;
}
