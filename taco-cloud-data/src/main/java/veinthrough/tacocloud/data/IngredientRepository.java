package veinthrough.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import veinthrough.tacocloud.model.Ingredient;
import static veinthrough.tacocloud.model.Ingredient.INGREDIENT_TYPE;

public interface IngredientRepository
        extends CrudRepository<Ingredient, String> {
    Ingredient findByNameAndType
            (String name, INGREDIENT_TYPE type);
}
