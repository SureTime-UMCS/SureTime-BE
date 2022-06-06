# SureTime

### Reference Documentation

For further reference, please consider the following sections:

* [Spring Boot Tutorial - Build a Rest Api with MongoDB](https://www.youtube.com/watch?v=ssj0CGxv60k&t=457s&ab_channel=Amigoscode)
* [Project Lombok](https://projectlombok.org/)
* [Spring Security](https://octoperf.com/blog/2018/03/08/securing-rest-api-spring-security/#user-auth-uuid)
* [Spring Security - 2](https://www.bezkoder.com/spring-boot-jwt-auth-mongodb/)
* [Hexagonal Architecture](https://www.baeldung.com/hexagonal-architecture-ddd-spring)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### Developers recommendations

Use a [IntelliJ IDEA Ultimate](https://www.jetbrains.com/lp/intellij-frameworks/)

Use a [API-DOCS](http://localhost:8080/swagger-ui.html)
        ```http://localhost:8080/swagger-ui.html```
#### Requirements

* [ Maven](https://maven.apache.org/)
    - [Windows](https://mkyong.com/maven/how-to-install-maven-in-windows/)
    - [Linux](https://maven.apache.org/install.html)
* [ Docker](https://www.docker.com/)

#### How run?

1. Run docker
    ````
    docker-compose up -d 
    ````

2. Run SureTime application
    ```
    mvn spring-boot:run
    ```
    
3. Login to API:
      ```
    curl --location --request POST 'http://localhost:8080/api/auth/login' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "username":"admin",
    "password":"password"
    }'
      ```
6. Authorize swagger endpoints.
        Login to app use 'http://localhost:8080/api/auth/login', you receive token that you must paste to swagger authorization (green lock) in this format "Bearer <token>.
### Project requirements for 4

- [x] Wykorzystanie narzędzia Spring Framework (najlepiej z wykorzystaniem SpringBoot) + Maven + git (repozytorium w
  ogólnie dostępnym darmowym hostingu)

- [x] Kod ma być dostępny w zewnętrznym repozytorium razem z dokumentacją

- [x] min 10 poprawnych (zgodnych z REST) endpoint-ów (min. 4 metody GET / POST / PUT / DELETE) Należy znać sens
  wykorzystywanych metod.

- [x] Do wszystkich endpointów należy przygotować kolekcję requestów za pomocą programu Postman (wyeksportowana kolekcja
  ma znaleźć się w repozytorium razem z kodem oraz dokumentacją w oddzielnych katalogach) - u nas ten podpunkt realizuje swagger

- [x] min 5 tabel w bazie wykorzystywanej przez aplikację

- [x] Logika biznesowa wykorzystująca DI oraz IoC

- [x] Dokumentacja opisująca przeznaczenie oraz przypadki użycia aplikacji w postaci diagramów UML

### Project requirements for grade > 4

- [x]  Osiągnięcie "glory of rest" (hateoas)
- [x] Wykorzystanie do projektowania i dokumentacji API narzędzia typu [swagger.io](https://swagger.io/) lub
  alternatywnych (Postman może importować ze swaggera)
- [x] Bardziej złożona logika wykorzystująca wzorce projektowe ale nie wymuszane przez framework Spring
- [x] Poprawnie wykorzystać jeden ze starterów aplikacji SpringBoot’a

### Dla chętnych

- [x] Prosty front-end do konsumpcji endpointów Rest-owych
- [x] Wykorzystać JWT jwt.io


