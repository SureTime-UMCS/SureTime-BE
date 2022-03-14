package com.assigment.suretime.competitor;

import com.assigment.suretime.common.Gender;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Competitor {
    @Id
    private String id;

    private String firstName;
    private String secondName;
    private Gender gender;

    @Indexed(unique = true)
    private String email;

    private Address address;
    private LocalDateTime created;

    public Competitor(String firstName, String secondName, Gender gender, String email, Address address, LocalDateTime created) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.created = created;
    }
}
