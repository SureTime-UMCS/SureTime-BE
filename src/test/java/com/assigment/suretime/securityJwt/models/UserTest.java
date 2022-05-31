package com.assigment.suretime.securityJwt.models;

import com.assigment.suretime.securityJwt.domain.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


class UserTest {





    @Test
    void createWithConstructor(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User("user", "user@gmail.com", encoder.encode("password"));
    }
}