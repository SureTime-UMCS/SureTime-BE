package com.assigment.suretime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SureTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SureTimeApplication.class, args);
    }
}
