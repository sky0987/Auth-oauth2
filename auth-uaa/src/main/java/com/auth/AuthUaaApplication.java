package com.auth;



import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Slf4j
@EnableWebMvc
public class AuthUaaApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthUaaApplication.class, args);
    }

}
