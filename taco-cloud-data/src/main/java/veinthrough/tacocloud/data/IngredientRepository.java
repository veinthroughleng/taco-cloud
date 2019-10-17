package veinthrough.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import veinthrough.tacocloud.model.Ingredient;

public interface IngredientRepository
        extends CrudRepository<Ingredient, String> {
}
