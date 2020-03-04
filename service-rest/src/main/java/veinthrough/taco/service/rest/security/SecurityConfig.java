package veinthrough.taco.service.rest.security;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import veinthrough.taco.property.SecurityProps;
import veinthrough.taco.service.rest.security.exception.Http401AuthenticationEntryPoint;
import veinthrough.utils.MethodLog;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private SecurityProps securityProps;


    private static final String[] URL_H2_CONSOLE = {
            "/h2-console",
            "/h2-console/**"
    };

    private static final String[] URL_DATA_REST = {
            "/rest/ingredients",
            "/rest/ingredients/**",
            "/rest/tacos",
            "/rest/tacos/**",
            "/rest/orders",
            "/rest/orders/**",
            "/rest/users",
            "/rest/users/**"
    };

    private static String[] URL_WHITELIST = {
            // -- register url
            "/rest/ingredients",
            "/rest/ingredients/**",

            // -- login
            "/login",
            "/signin",

            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, SecurityProps securityProps) {
        this.userDetailsService = userDetailsService;
        this.securityProps = securityProps;

        if (!securityProps.isH2ConsoleSecured()) {
            List<String> list = Lists.newArrayList(URL_WHITELIST);
            list.addAll(Lists.newArrayList(URL_H2_CONSOLE));
            URL_WHITELIST = list.toArray(new String[0]);
            // [DEBUG]
            log.info(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                    "isH2ConsoleSecured", String.valueOf(securityProps.isH2ConsoleSecured()),
                    "URL_WHITELIST", Arrays.toString(URL_WHITELIST)));
        }
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                //.antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
                .anyRequest().authenticated().and()

                .exceptionHandling()
                .authenticationEntryPoint(
                        new Http401AuthenticationEntryPoint("Basic realm=\"Taco Cloud\"")).and()

                //.addFilter(new JWTLoginFilter(authenticationManager(), securityProps.getSigningKey()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), securityProps.getSigningKey()))

                // logout
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll().and()

                // ignore csrf: for debug purposes
                //.csrf()
                //.ignoringAntMatchers(URL_H2_CONSOLE).and()
                // NOTE: Result in all POST requests be ignored
                //.csrf()
                //.ignoringAntMatchers(URL_DATA_REST).and()

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .headers()
                .frameOptions()
                .sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //Spring Data JPA authentication/UserDetailsService
        auth.authenticationProvider(
                // CustomAuthenticationProvider used,
                // JWTLoginFilter will never be accessed
                new CustomAuthenticationProvider(
                        userDetailsService, encoder()));
    }
}
