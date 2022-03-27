package com.assigment.suretime;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
@EnableSwagger2
@OpenAPIDefinition(info = @Info(title = "SureTime API", version = "0.1", description = "With ous you are sure."))
public class SureTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SureTimeApplication.class, args);
    }
}
