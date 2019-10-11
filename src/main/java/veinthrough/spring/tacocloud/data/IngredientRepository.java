package veinthrough.spring.tacocloud.data;

import veinthrough.spring.tacocloud.data.model.Ingredient;

import java.util.List;
import java.util.Map;

public interface IngredientRepository {

    Iterable<Ingredient> getAll();

    Ingredient getOneById(String id);

    Ingredient save(Ingredient ingredient);

    List<Ingredient> getIngredients();

    Map<Ingredient.INGREDIENT_TYPE, List<Ingredient>> getTypedIngredients();
}
