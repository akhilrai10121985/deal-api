package com.casestudy.DealAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.casestudy.*")
@ComponentScan(basePackages = {"com.casestudy.*"})
@EntityScan("com.casestudy.*")
public class DealApiApplication {

    public static void main(String[] args) {
        // Launch the application
        SpringApplication.run(DealApiApplication.class, args);
    }

}
