package veinthrough.taco.service.rest.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static veinthrough.utils.Constants.URL_DATA_OTHERS;

@Configuration
@EnableResourceServer
class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("service-rest").stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(URL_DATA_OTHERS).authenticated()
//                .antMatchers(URL_DATA_OTHERS).hasAuthority("#oauth2.hasScope('message:read')")
                .anyRequest().permitAll().and()

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .headers()
                .frameOptions()
                .sameOrigin();
    }
}
