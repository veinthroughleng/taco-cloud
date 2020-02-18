package veinthrough.taco.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Mono;
import veinthrough.taco.model.Ingredient;
import static veinthrough.taco.model.Ingredient.INGREDIENT_TYPE;

@CrossOrigin(origins="*")
public interface IngredientRepository
        extends ReactiveCrudRepository<Ingredient, String> {
    Mono<Ingredient> findByNameAndType
            (String name, INGREDIENT_TYPE type);
}
