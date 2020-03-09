package veinthrough.taco.service.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import veinthrough.taco.model.User;
import veinthrough.taco.model.href.UserMix;
import veinthrough.taco.service.app.feign.RestClient;
import veinthrough.taco.utils.MethodLog;

@Service
@Primary
@Slf4j
public class RestUserDetailsServiceImpl implements UserDetailsService {
    private RestClient rest;

    public RestUserDetailsServiceImpl(RestClient rest) {
        this.rest = rest;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Resource<User> user = rest.userByName(username);
        if (user != null && user.getContent() != null) {
            log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                    "user", user.getContent().toString()));
            UserMix userMix = new UserMix(user);
            log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                    "user mix", userMix.toString()));
            return new UserMix(user);
        }
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "can't get user by name",
                "user name", username));
        throw new UsernameNotFoundException("User '" + username + "' not found!");
    }
}
