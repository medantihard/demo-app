package com.example.demo_app;

import com.example.demo_app.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.demo_app.model.RegisterRequest;

import java.util.logging.Logger;

import static com.example.demo_app.model.Role.ADMIN;
import static com.example.demo_app.model.Role.MANAGER;

@SpringBootApplication
public class DemoAppApplication {

    Logger logger = Logger.getLogger(getClass().getName());

    private static final String ADMININFO = "Admin";

    public static void main(String[] args) {
        SpringApplication.run(DemoAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname(ADMININFO)
                    .lastname(ADMININFO)
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            logger.info("Admin token: " + service.register(admin).getAccessToken());

            var manager = RegisterRequest.builder()
                    .firstname(ADMININFO)
                    .lastname(ADMININFO)
                    .email("manager@mail.com")
                    .password("password")
                    .role(MANAGER)
                    .build();
            logger.info("Manager token: " + service.register(manager).getAccessToken());

        };
    }
}
