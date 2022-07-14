# SureTime

### Reference Documentation

For further reference, please consider the following sections:

* [Spring Boot Tutorial - Build a Rest Api with MongoDB](https://www.youtube.com/watch?v=ssj0CGxv60k&t=457s&ab_channel=Amigoscode)
* [Project Lombok](https://projectlombok.org/)
* [Spring Security](https://octoperf.com/blog/2018/03/08/securing-rest-api-spring-security/#user-auth-uuid)
* [Spring Security - 2](https://www.bezkoder.com/spring-boot-jwt-auth-mongodb/)
* [Hexagonal Architecture](https://www.baeldung.com/hexagonal-architecture-ddd-spring)

### Developers recommendations

Use a [IntelliJ IDEA Ultimate](https://www.jetbrains.com/lp/intellij-frameworks/)

Use a [API-DOCS](http://localhost:8080/swagger-ui.html)
        ```http://localhost:8080/swagger-ui.html```
#### Requirements

* [ Maven](https://maven.apache.org/)
    - [Windows](https://mkyong.com/maven/how-to-install-maven-in-windows/)
    - [Linux](https://maven.apache.org/install.html)
* [ Docker](https://www.docker.com/)

#### Project members:
* Szymon Sala - Lider, BackEnd
* [Szymon Żywko - BackEnd](https://www.linkedin.com/in/szymon-%C5%BCywko-35759a1b7/)
* [Przemek Sałek - FrontEnd](https://www.linkedin.com/in/przemyslaw-salek/)
* Dorota Zaremba - Dokumentacja
* Aleksander Pitucha - BackEnd


#### Used technology:
* swagger
* glory of rest (hateoas)
* JWT Authentication
* caffeine cache
* mongoDB

#### How to run?

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
4. Authorize swagger endpoints.
        Login to app use 'http://localhost:8080/api/auth/login', you receive token that you must paste to swagger authorization (green lock) in this format "Bearer your_token".


