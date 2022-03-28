package com.assigment.suretime.securityJwt.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


class UserTest {





    @Test
    void createWithConstructor(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User("user", "user@gmail.com", encoder.encode("password"));
    }
}