package com.assigment.suretime.club;

import com.assigment.suretime.address.Address;
import com.assigment.suretime.person.Person;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Document
public class Club {
    @Id
    private String id;
    private Address address;
    @Indexed(unique = true)
    private String name;
    @DocumentReference
    private List<Person> members;

    public Club(Address address, String name) {
        this.address = address;
        this.name = name;
    }

}
