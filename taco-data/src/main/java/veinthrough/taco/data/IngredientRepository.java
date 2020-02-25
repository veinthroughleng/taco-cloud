package veinthrough.taco.data;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import veinthrough.taco.model.Ingredient;
import static veinthrough.taco.model.Ingredient.INGREDIENT_TYPE;

@Profile("rest")
public interface IngredientRepository
        extends CrudRepository<Ingredient, String> {
    Ingredient findByNameAndType
            (String name, INGREDIENT_TYPE type);
}
