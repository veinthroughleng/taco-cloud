package veinthrough.taco;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import veinthrough.taco.data.IngredientRepository;
import veinthrough.taco.data.TacoRepository;
import veinthrough.taco.data.UserRepository;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.User;
import veinthrough.utils.MethodLog;
import static veinthrough.taco.Ingredients.*;

@Profile("dev")
@Configuration
@Slf4j
public class DevConfig {
    @Bean
    public CommandLineRunner dataLoader(@Autowired IngredientRepository ingredientRepo,
                                        @Autowired UserRepository userRepo,
                                        @Autowired TacoRepository tacoRepo,
                                        @Autowired PasswordEncoder encoder) {
        //[DEBUG]
        log.info(MethodLog.log("DevConfig.dataLoader"));

        return args ->
        {
            ingredientRepo.saveAll(
                    Lists.newArrayList(
                            FLTO, COTO, GRBF, CARN, TMTO, LETC, CHED, JACK, SLSA, SRCR));

            userRepo.save(new User("veinthrough", encoder.encode("123456"),
                    "Craig Walls", "123 North Street", "Cross Roads", "TX",
                    "76227", "123-123-1234"));

            tacoRepo.saveAll(
                    Lists.newArrayList(
                            Taco.builder().name("Carnivore")
                                    .ingredients(Lists.newArrayList(
                                            FLTO, GRBF, CARN, SRCR, SLSA, CHED))
                                    .build(),
                            Taco.builder().name("Bovine Bounty")
                                    .ingredients(Lists.newArrayList(
                                            CARN, GRBF, CHED, JACK, SRCR))
                                    .build(),
                            Taco.builder().name("Veg-Out")
                                    .ingredients(Lists.newArrayList(
                                            FLTO, CARN, TMTO, LETC, SLSA))
                                    .build())
            );
        };
    }
}
