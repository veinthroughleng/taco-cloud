package veinthrough.taco.rest.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import veinthrough.taco.security.SecurityUtils;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private String[] URL_WHITELIST;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          SecurityUtils securityUtils,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        URL_WHITELIST = securityUtils.getWhiteList();
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
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
                // needed for Angular/CORS
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated().and()

                // logout
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll().and()

                // ignore csrf: for debug purposes
//                .csrf()
//                .ignoringAntMatchers(URL_H2_CONSOLE).and()
                // NOTE: Result in all POST requests be ignored
//                .csrf()
//                .ignoringAntMatchers(URL_DATA_REST).and()

                // Allow pages to be loaded in frames from the same origin;
                // needed for H2-Console
                .headers()
                .frameOptions()
                .sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Spring Data JPA authentication/UserDetailsService
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        /*
        //in memory authentication
        auth.inMemoryAuthentication()
                .withUser("buzz")
                .password("infinity")
                .authorities("ROLE_USER")
                .and()
                .withUser("woody")
                .password("bullseye")
                .authorities("ROLE_USER");
                */

        /*
        //jdbc authentication
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from Users " +
                                "where username=?")
                .authoritiesByUsernameQuery(
                        "select username, authority from UserAuthorities " +
                                "where username=?")
                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
                */

        /*
        //LDAP authentication with remote LDAP server
        auth.ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("member={0}")
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("passcode")
                .contextSource()
                .url("ldap://taco.com:389/dc=taco,dc=com");
                */

        /*
        //LDAP authentication with embedded LDAP server
        auth.ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("member={0}")
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("passcode")
                .contextSource()
                .root("dc=taco,dc=com")
                .ldif("classpath:users.ldif");
                */
    }
}
