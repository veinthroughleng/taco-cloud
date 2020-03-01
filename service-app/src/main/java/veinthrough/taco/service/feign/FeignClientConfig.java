package veinthrough.taco.service.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class FeignClientConfig {
//    @Bean
//    @Scope("prototype")
//    public Feign.Builder feignHystrixBuilder() {
//        return Feign.builder();
//    }
//
//    @Bean
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }
}