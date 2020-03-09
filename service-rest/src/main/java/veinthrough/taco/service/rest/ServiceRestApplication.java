package veinthrough.taco.service.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"veinthrough.taco"})
@EnableJpaRepositories(basePackages = {"veinthrough.taco.data"})
@EntityScan(basePackages = {"veinthrough.taco.model"})
public class ServiceRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceRestApplication.class, args);
    }
}
