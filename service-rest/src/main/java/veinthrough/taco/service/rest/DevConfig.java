package veinthrough.taco.service.rest;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import veinthrough.taco.data.IngredientRepository;
import veinthrough.taco.data.OrderRepository;
import veinthrough.taco.data.TacoRepository;
import veinthrough.taco.data.UserRepository;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.User;
import veinthrough.taco.utils.MethodLog;

import static veinthrough.taco.model.DefaultIngredients.*;

@Profile("dev")
@Configuration
@Slf4j
public class DevConfig {
    @Bean
    public CommandLineRunner dataLoader(@Autowired IngredientRepository ingredientRepo,
                                        @Autowired UserRepository userRepo,
                                        @Autowired TacoRepository tacoRepo,
                                        @Autowired OrderRepository orderRepo,
                                        @Autowired PasswordEncoder encoder) {
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName()));

        return args ->
        {
            ingredientRepo.saveAll(
                    Lists.newArrayList(
                            FLTO, COTO, GRBF, CARN, TMTO, LETC, CHED, JACK, SLSA, SRCR));

            User veinthrough1 = userRepo.save(new User("veinthrough1", encoder.encode("123456"),
                    "Veinthrough Leng 1", "123 North Street", "Cross Roads", "TX",
                    "76227", "123-123-1234"));
            User veinthrough2 = userRepo.save(new User("veinthrough2", encoder.encode("123456"),
                    "Veinthrough Leng 2", "123 North Street", "Cross Roads", "TX",
                    "76227", "123-123-1234"));
            // a user without order
            User veinthrough3 = userRepo.save(new User("veinthrough3", encoder.encode("123456"),
                    "Veinthrough Leng 3", "123 North Street", "Cross Roads", "TX",
                    "76227", "123-123-1234"));

            Taco carnivore = tacoRepo.save(new Taco("Carnivore",
                    Lists.newArrayList(
                            FLTO, GRBF, CARN, SRCR, SLSA, CHED)));
            Taco bovineBounty = tacoRepo.save(new Taco("Bovine Bounty",
                    Lists.newArrayList(
                            CARN, GRBF, CHED, JACK, SRCR)));
            Taco vegOut = tacoRepo.save(new Taco("Veg-Out",
                    Lists.newArrayList(
                            FLTO, CARN, TMTO, LETC, SLSA)));

            orderRepo.save(Order.builder()
                    .user(veinthrough1)
                    .name("veinthrough")
                    .street("19th")
                    .city("Haidian")
                    .state("Beijing")
                    .zip("100038")
                    .ccNumber("0123456789")
                    .ccExpiration("05/19")
                    .ccCVV("246")
                    .tacos(Lists.newArrayList(carnivore, bovineBounty))
                    .build());

            orderRepo.save(Order.builder()
                    .user(veinthrough2)
                    .name("veinthrough")
                    .street("19th")
                    .city("Haidian")
                    .state("Beijing")
                    .zip("100038")
                    .ccNumber("0123456789")
                    .ccExpiration("05/19")
                    .ccCVV("246")
                    .tacos(Lists.newArrayList(bovineBounty, vegOut))
                    .build());
        };
    }
}
