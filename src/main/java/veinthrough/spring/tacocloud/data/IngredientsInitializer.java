package veinthrough.spring.tacocloud.data;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import veinthrough.spring.tacocloud.data.model.Ingredient;

import static veinthrough.spring.tacocloud.data.model.Ingredient.INGREDIENT_TYPE;

import java.util.List;

@Component
public class IngredientsInitializer {
    private static final List<Ingredient> ingredients;
    private static boolean initialized = false;
    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientsInitializer(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    static {
        ingredients = Lists.newArrayList(
                new Ingredient("FLTO", "Flour Tortilla", INGREDIENT_TYPE.WRAP),
                new Ingredient("COTO", "Corn Tortilla", INGREDIENT_TYPE.WRAP),
                new Ingredient("GRBF", "Ground Beef", INGREDIENT_TYPE.PROTEIN),
                new Ingredient("CARN", "Carnitas", INGREDIENT_TYPE.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", INGREDIENT_TYPE.VEGGIES),
                new Ingredient("LETC", "Lettuce", INGREDIENT_TYPE.VEGGIES),
                new Ingredient("CHED", "Cheddar", INGREDIENT_TYPE.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", INGREDIENT_TYPE.CHEESE),
                new Ingredient("SLSA", "Salsa", INGREDIENT_TYPE.SAUCE),
                new Ingredient("SRCR", "Sour Cream", INGREDIENT_TYPE.SAUCE)
        );
    }

    public void initialize() {
        if (!initialized) {
            ingredientRepo.saveAll(ingredients);
        }
    }

    public void initialize(Iterable<Ingredient> others) {
        if (!initialized) {
            ingredientRepo.saveAll(others);
        }
    }
}
