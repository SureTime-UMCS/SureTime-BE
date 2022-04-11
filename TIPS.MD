## TIPS FROM Zywko

#### To check if current user is admin
If you want to check in code if current user is admin do:
    ```
    AuthenticationFacade.isAdmin()
    ```

If you want to allow certain endpoint only for users with role do:
   ```
   @PreAuthorize("hasRole('ADMIN')")
   ```
or allow someone else use:
   ```
   @PreAuthorize("hasRole('ADMIN') or hasRole('MOD')"
   ```

#### Dealing with object in request body.

If you want to your endpoint in body has object
for example 

```java
    @PutMapping("{clubName}/add_moderators")
    ResponseEntity<?> addModeratorsToClub(
            @PathVariable String clubName,
            @RequestBody @Valid AddPersonsToClubModeratorModel persons){
        return service.addModeratorsToClub(clubName, persons);
    }
```

```java
@NoArgsConstructor
@Data
public class AddPersonsToClubModeratorModel {
    private List<String> personEmails;

    @PersistenceConstructor
    public AddPersonsToClubModeratorModel(List<String> personEmails) {
        this.personEmails = personEmails;
    }
}
```
When creating that new model it's important to:
- provide empty constructor `@NoArgsConstructor` 
- provide `@PersistanceConstructor` with such arguments witch are passed throw json object when calling endpoint. 
Ex. Given `AddPersonToClubModeratorModel` a json need to look like this:
    ```{
      "roles": [
        "ROLE_ADMIN", "ROLE_MOD"
      ]
    }
  ```

#### Dealing with complicated models

for example: 
```@Getter
@Setter
@Document
public class Person {
    @Id
    private String id;
    private String firstName;
    private String secondName;

    @Indexed(unique = true)
    private String email;
    private Gender gender;

    @DocumentReference
    private Club club;

    @DocumentReference
    private Person coach;

    @DocumentReference
    @JsonIgnore
    private User user;

    @JsonIgnore
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime created = LocalDateTime.now();
    
   ```
This model is kinda huge. It would be hard to pass it as json. 
In this situations use simplified DTO object. 
For example for Person i created PersonDTO
```
    @Data
    @NoArgsConstructor
    public class PersonDTO {
    
        private String firstName;
        private String secondName;
        private String email;
        private Gender gender;
        private String clubName;
        private String coachEmail; 
    }
    ... all args contructor   
 ```

Then in service other methods are responsible for finding for example Person coach object from coachEmail, Club object from clubName