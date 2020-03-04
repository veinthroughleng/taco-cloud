package veinthrough.taco.service.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Component;
import veinthrough.utils.MethodLog;

import java.time.LocalDateTime;

@Configuration
@EnableFeignClients
@Slf4j
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

//    @Bean
//    public OAuth2FeignRequestInterceptor requestInterceptor() {
//        OAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
//        ResourceOwnerPasswordResourceDetails resourceDetails =
//                new ResourceOwnerPasswordResourceDetails();
//        resourceDetails.setUsername("veinthrough");
//        resourceDetails.setPassword("123456");
//        return new OAuth2FeignRequestInterceptor(clientContext, resourceDetails);
//    }

    @Value("${security.jwt.token}")
    private String jwtToken;

    @Bean
    public RequestInterceptor requestInterceptor() {
        // [DEBUG]
        log.info(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "token", jwtToken));

        return requestTemplate -> {
            requestTemplate.header("Authorization", jwtToken);
        };
    }
}
