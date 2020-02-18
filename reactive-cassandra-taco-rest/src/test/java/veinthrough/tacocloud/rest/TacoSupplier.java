package veinthrough.taco.rest;

import veinthrough.taco.model.IngredientUDT;
import veinthrough.taco.model.Taco;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static veinthrough.taco.model.Ingredient.INGREDIENT_TYPE.PROTEIN;
import static veinthrough.taco.model.Ingredient.INGREDIENT_TYPE.WRAP;

class TacoSupplier {

    static Taco getTaco(Long number) {
        Taco taco = new Taco();
        taco.setId(UUID.randomUUID());
        taco.setName("Taco " + number);
        List<IngredientUDT> ingredients = new ArrayList<>();
        ingredients.add(
                new IngredientUDT("Ingredient A", WRAP));
        ingredients.add(
                new IngredientUDT("Ingredient B", PROTEIN));
        taco.setIngredients(ingredients);
        return taco;
    }
}
