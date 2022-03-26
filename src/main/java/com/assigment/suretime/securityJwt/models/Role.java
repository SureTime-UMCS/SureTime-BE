package com.assigment.suretime.securityJwt.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Document(collection = "roles")
@NoArgsConstructor
public class Role {
  @Id
  private String id;

  protected ERole name;

  public Role(ERole name) {
    this.name = name;
  }

}
