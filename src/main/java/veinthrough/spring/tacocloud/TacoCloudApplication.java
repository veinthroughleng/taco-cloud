package veinthrough.spring.tacocloud;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import veinthrough.spring.tacocloud.data.IngredientRepository;
import veinthrough.spring.tacocloud.data.model.Ingredient;

import java.util.List;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(@Autowired IngredientRepository ingredientRepo) {
        return args -> ingredientRepo.saveAll(
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
    }

}
