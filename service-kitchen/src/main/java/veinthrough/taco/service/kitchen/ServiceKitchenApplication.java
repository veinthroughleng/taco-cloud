package veinthrough.taco.service.kitchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"veinthrough.taco"})
public class ServiceKitchenApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceKitchenApplication.class, args);
    }
}
