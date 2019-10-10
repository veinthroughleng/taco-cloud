package veinthrough.spring.tacocloud.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import veinthrough.spring.tacocloud.data.model.Ingredient;
import static veinthrough.spring.tacocloud.data.model.Ingredient.INGREDIENT_TYPE;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Ingredients {

    private static List<Ingredient> getIngredients() {
        return Lists.newArrayList(
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

    public static Map<INGREDIENT_TYPE, List<Ingredient>> getTypedIngredients() {
        return getIngredients().stream()
                .collect(Collectors.groupingBy(Ingredient::getType));
    }
}
