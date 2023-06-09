package com.tr.web111;

import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Api

public class Web111Application {

    public static void main(String[] args) {
        SpringApplication.run(Web111Application.class, args);
    }

}
