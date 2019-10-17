package veinthrough.tacocloud;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import veinthrough.tacocloud.data.IngredientRepository;
import veinthrough.tacocloud.data.UserRepository;
import veinthrough.tacocloud.model.Ingredient;
import veinthrough.tacocloud.model.User;
import veinthrough.utils.MethodLog;

@Profile("dev")
@Configuration
@Slf4j
public class DevConfig {
    @Bean
    public CommandLineRunner dataLoader(@Autowired IngredientRepository ingredientRepo,
                                        @Autowired UserRepository userRepo,
                                        @Autowired PasswordEncoder encoder) {
        //[DEBUG]
        log.info(MethodLog.inLog("DevConfig.dataLoader"));

        return args ->
            {
                ingredientRepo.saveAll(
                        Lists.newArrayList(
                                new Ingredient("FLTO", "Flour Tortilla", Ingredient.INGREDIENT_TYPE.WRAP),
                                new Ingredient("COTO", "Corn Tortilla", Ingredient.INGREDIENT_TYPE.WRAP),
                                new Ingredient("GRBF", "Ground Beef", Ingredient.INGREDIENT_TYPE.PROTEIN),
                                new Ingredient("CARN", "Carnitas", Ingredient.INGREDIENT_TYPE.PROTEIN),
                                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.INGREDIENT_TYPE.VEGGIES),
                                new Ingredient("LETC", "Lettuce", Ingredient.INGREDIENT_TYPE.VEGGIES),
                                new Ingredient("CHED", "Cheddar", Ingredient.INGREDIENT_TYPE.CHEESE),
                                new Ingredient("JACK", "Monterrey Jack", Ingredient.INGREDIENT_TYPE.CHEESE),
                                new Ingredient("SLSA", "Salsa", Ingredient.INGREDIENT_TYPE.SAUCE),
                                new Ingredient("SRCR", "Sour Cream", Ingredient.INGREDIENT_TYPE.SAUCE)));

                userRepo.save(new User("veinthrough", encoder.encode("123456"),
                        "Craig Walls", "123 North Street", "Cross Roads", "TX",
                        "76227", "123-123-1234"));
            };
    }
}
