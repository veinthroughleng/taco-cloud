package veinthrough.taco.service.app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.PortMapperConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import static veinthrough.taco.utils.Constants.*;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true,
        securedEnabled = true)
class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("taco-rest").stateless(false);

        // remote token services is needed only when
        // resource server is separate from authorization server
//        RemoteTokenServices tokenService = new RemoteTokenServices();
//        tokenService.setCheckTokenEndpointUrl(
//                "http://localhost:8080/spring-security-oauth-server/oauth/check_token");
//        tokenService.setClientId("resource-server");
//        tokenService.setClientSecret("123456");
//        resources.tokenServices(tokenService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // deny post ingredients
                .antMatchers(HttpMethod.POST, URL_DATA_INGREDIENTS).denyAll()
                // secure post tacos
                .antMatchers(HttpMethod.POST, URL_DATA_TACOS).authenticated()
                // secure orders and users
                .antMatchers(URL_DATA_ORDERS).authenticated()
                .antMatchers(URL_DATA_USERS).authenticated()
//                .antMatchers(URL_DATA_ORDERS).hasAuthority("#oauth2.hasScope('read')")
                .anyRequest().permitAll().and()

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                // SHOULD BE HERE
                .headers()
                .frameOptions()
                .sameOrigin();
    }
}
