package veinthrough.taco.service.feign;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableFeignClients
@EnableOAuth2Client
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

    private OAuth2ClientContext oAuth2ClientContext;

    @Autowired
    public FeignClientConfig(OAuth2ClientContext oAuth2ClientContext) {
        this.oAuth2ClientContext = oAuth2ClientContext;
    }

    @Bean
    public OAuth2FeignRequestInterceptor requestInterceptor() {
        // grant type: client_credentials
//        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
//        resourceDetails.setClientId("service-app");
//        resourceDetails.setClientSecret("123456");
//        resourceDetails.setAccessTokenUri("http://localhost:8081/oauth/token");
//        resourceDetails.setGrantType("client_credentials");
//        resourceDetails.setScope(Lists.newArrayList("select"));


        // grant type: password
        ResourceOwnerPasswordResourceDetails resourceDetails =
                new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setClientId("service-app");
        resourceDetails.setClientSecret("123456");
        resourceDetails.setAccessTokenUri("http://localhost:8081/oauth/token");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(Lists.newArrayList("select"));
        resourceDetails.setUsername("veinthrough");
        resourceDetails.setPassword("123456");
        return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, resourceDetails);
    }

//    @Value("${security.jwt.token}")
//    private String jwtToken;
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        // [DEBUG]
//        log.info(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
//                "token", jwtToken));
//
//        return requestTemplate -> {
//            requestTemplate.header("Authorization", jwtToken);
//        };
//    }
}
