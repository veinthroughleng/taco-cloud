package veinthrough.taco.service.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableHystrix
//@EnableCircuitBreaker
//@EnableHystrixDashboard
@ComponentScan(basePackages = {"veinthrough.taco"})
@EnableJpaRepositories(basePackages = {"veinthrough.taco.data"})
@EntityScan(basePackages = {"veinthrough.taco.model"})
public class ServiceAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAppApplication.class, args);
    }
}
