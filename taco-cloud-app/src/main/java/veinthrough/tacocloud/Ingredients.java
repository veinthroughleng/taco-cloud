package veinthrough.tacocloud;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import veinthrough.tacocloud.model.Ingredient;
import static veinthrough.tacocloud.model.Ingredient.INGREDIENT_TYPE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Ingredients {
    static final Ingredient FLTO = new Ingredient("FLTO", "Flour Tortilla", INGREDIENT_TYPE.WRAP);
    static final Ingredient COTO = new Ingredient("COTO", "Corn Tortilla", INGREDIENT_TYPE.WRAP);
    static final Ingredient GRBF = new Ingredient("GRBF", "Ground Beef", INGREDIENT_TYPE.PROTEIN);
    static final Ingredient CARN = new Ingredient("CARN", "Carnitas", INGREDIENT_TYPE.PROTEIN);
    static final Ingredient TMTO = new Ingredient("TMTO", "Diced Tomatoes", INGREDIENT_TYPE.VEGGIES);
    static final Ingredient LETC = new Ingredient("LETC", "Lettuce", INGREDIENT_TYPE.VEGGIES);
    static final Ingredient CHED = new Ingredient("CHED", "Cheddar", INGREDIENT_TYPE.CHEESE);
    static final Ingredient JACK = new Ingredient("JACK", "Monterrey Jack", INGREDIENT_TYPE.CHEESE);
    static final Ingredient SLSA = new Ingredient("SLSA", "Salsa", INGREDIENT_TYPE.SAUCE);
    static final Ingredient SRCR = new Ingredient("SRCR", "Sour Cream", INGREDIENT_TYPE.SAUCE);
}
