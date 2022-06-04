package com.assigment.suretime.club.domain;

import com.assigment.suretime.club.application.request.ClubDTO;
import com.assigment.suretime.generics.models.Address;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
public class Club {
    @Id
    private String id;
    private Address address;
    private String name;

    @Indexed(unique = true)
    private String clubUUID;

    private Set<String> members, clubModerators;

    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime created = LocalDateTime.now();

    @PersistenceConstructor
    public Club(Address address, String name) {
        this.address = address;
        this.clubModerators = new HashSet<>();
        this.members = new HashSet<>();
        this.name = name;
        this.clubUUID = UUID.randomUUID().toString();
    }

    public Club(ClubDTO club) {
        this.name = club.getName();
        this.clubModerators = club.getClubModerators();
        this.members = club.getMembers();
        this.address = club.getAddress();
        this.clubUUID = UUID.randomUUID().toString();
    }
}
