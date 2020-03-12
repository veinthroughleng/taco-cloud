package veinthrough.taco.service.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import veinthrough.taco.security.SecurityUtils;
import veinthrough.taco.utils.MethodLog;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private final String[] URL_WHITELIST;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, SecurityUtils securityUtils, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.URL_WHITELIST = securityUtils.getWhiteList();
        this.passwordEncoder = passwordEncoder;
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "user details service", this.userDetailsService.toString(),
                "white list", Arrays.toString(this.URL_WHITELIST),
                "password encoder", this.passwordEncoder.toString()));
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // needed for Angular/CORS
                .requestMatchers().antMatchers(HttpMethod.OPTIONS).and()
                .cors().and().csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated().and()

                // logout
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();

                // ignore csrf: for debug purposes
//                .csrf()
//                .ignoringAntMatchers(URL_H2_CONSOLE).and()
                // NOTE: Result in all POST requests be ignored
//                .csrf()
//                .ignoringAntMatchers(URL_DATA_REST).and()
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Spring Data JPA authentication/UserDetailsService
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
