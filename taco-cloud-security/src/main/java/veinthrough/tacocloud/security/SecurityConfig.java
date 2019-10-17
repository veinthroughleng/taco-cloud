package veinthrough.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/design", "/orders", "/discount")
                    .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**")
                    .permitAll()

                //login
                .and()
                    .formLogin()
                        .loginPage("/login")
                        //By default, a successful login will take the user directly to previous accessed page
                        //.defaultSuccessUrl("/design", true)

                //logout
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")

                //Make H2-Console non-secured; for debug purposes
                .and()
                    .csrf()
                        .ignoringAntMatchers("/h2-console/**")

                //Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .and()
                    .headers()
                        .frameOptions()
                            .sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Spring Data JPA authentication/UserDetailsService
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());


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
                .url("ldap://tacocloud.com:389/dc=tacocloud,dc=com");
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
                .root("dc=tacocloud,dc=com")
                .ldif("classpath:users.ldif");
                */
    }
}
