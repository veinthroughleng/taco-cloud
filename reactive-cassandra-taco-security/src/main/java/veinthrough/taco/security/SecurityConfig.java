package veinthrough.taco.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@ComponentScan
@Deprecated
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
                .pathMatchers("/design", "/orders/**", "/discount")
                .permitAll()
                //.access("hasRole('ROLE_USER')")
                .pathMatchers(HttpMethod.PATCH, "/ingredients")
                .permitAll()
                .pathMatchers("/", "/**")
                .permitAll()

                //login
                .and()
                .formLogin()
                .loginPage("/login")
                //By default, a successful login will take the user directly to previous accessed page
                //.defaultSuccessUrl("/design", true)

                .and()
                .httpBasic()
//                .realmName("Taco Cloud")

                //logout
                .and()
                .logout()
                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")

                //Make H2-Console non-secured; for debug purposes
//                .and()
//                .csrf()
//                        .ignoringAntMatchers("/h2-console/**", "/ingredients/**", "/design", "/orders/**",
//                                "/rest/tacos", "/rest/orders")

                //Allow pages to be loaded in frames from the same origin; needed for H2-Console
//                .and()
//                    .headers()
//                        .frameOptions()
//                            .sameOrigin()

                .and()
                .build();
    }
}
