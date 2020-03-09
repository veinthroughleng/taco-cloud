package veinthrough.taco.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import static veinthrough.taco.model.Ingredient.INGREDIENT_TYPE.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultIngredients {
    public static final Ingredient FLTO = new Ingredient("FLTO", "Flour Tortilla", WRAP);
    public static final Ingredient COTO = new Ingredient("COTO", "Corn Tortilla", WRAP);
    public static final Ingredient GRBF = new Ingredient("GRBF", "Ground Beef", PROTEIN);
    public static final Ingredient CARN = new Ingredient("CARN", "Carnitas", PROTEIN);
    public static final Ingredient TMTO = new Ingredient("TMTO", "Diced Tomatoes", VEGGIES);
    public static final Ingredient LETC = new Ingredient("LETC", "Lettuce", VEGGIES);
    public static final Ingredient CHED = new Ingredient("CHED", "Cheddar", CHEESE);
    public static final Ingredient JACK = new Ingredient("JACK", "Monterrey Jack", CHEESE);
    public static final Ingredient SLSA = new Ingredient("SLSA", "Salsa", SAUCE);
    public static final Ingredient SRCR = new Ingredient("SRCR", "Sour Cream", SAUCE);
    // for feign-hystrix
    public static final Ingredient VEIN = new Ingredient("VEIN", "Veinthrough", SAUCE);
}
