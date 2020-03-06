package veinthrough.taco.service.rest.security;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
import veinthrough.utils.MethodLog;

import java.util.Arrays;
import java.util.List;

import static veinthrough.utils.Constants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private SecurityProps securityProps;
    private static String[] URL_WHITELIST = null;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, SecurityProps securityProps) {
        this.userDetailsService = userDetailsService;
        this.securityProps = securityProps;
        updateWhiteList();
    }

    private void updateWhiteList() {
        List<String> whiteList = Lists.newArrayList(URL_LOGIN);
//        whiteList.addAll(Lists.newArrayList(URL_DATA_INGREDIENTS));
        whiteList.addAll(Lists.newArrayList(URL_SWAGGER));
        if (!securityProps.isH2ConsoleSecured()) {
            whiteList.addAll(Lists.newArrayList(URL_H2_CONSOLE));
            URL_WHITELIST = whiteList.toArray(new String[0]);
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
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
//                .antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
                .anyRequest().authenticated();

//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        new Http401AuthenticationEntryPoint("Basic realm=\"Taco Cloud\"")).and()

//                .addFilter(new JWTLoginFilter(authenticationManager(), securityProps.getSigningKey()))
//                .addFilter(new JWTAuthenticationFilter(authenticationManager(), securityProps.getSigningKey()))

                // logout
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")
//                .permitAll().and()

                // ignore csrf: for debug purposes
//                .csrf()
//                .ignoringAntMatchers(URL_H2_CONSOLE).and()
//                 NOTE: Result in all POST requests be ignored
//                .csrf()
//                .ignoringAntMatchers(URL_DATA_REST);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Spring Data JPA authentication/UserDetailsService
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }
}
