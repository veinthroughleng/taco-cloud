package veinthrough.taco.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import veinthrough.taco.data.UserRepository;
import veinthrough.taco.model.User;
import veinthrough.utils.MethodLog;

@Profile("dev")
@Configuration
@Slf4j
public class DevConfig {
    @Bean
    public CommandLineRunner dataLoader(@Autowired UserRepository userRepo,
                                        @Autowired PasswordEncoder encoder) {
        //[DEBUG]
        log.info(MethodLog.inLog("DevConfig.dataLoader"));

        return args ->
                userRepo.save(new User("veinthrough", encoder.encode("123456"),
                        "Craig Walls", "123 North Street", "Cross Roads", "TX",
                        "76227", "123-123-1234"));
    }
}
