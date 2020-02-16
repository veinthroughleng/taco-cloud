package veinthrough.tacocloud.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Mono;
import veinthrough.tacocloud.model.Ingredient;
import static veinthrough.tacocloud.model.Ingredient.INGREDIENT_TYPE;

@CrossOrigin(origins="*")
public interface IngredientRepository
        extends ReactiveCrudRepository<Ingredient, String> {
    Mono<Ingredient> findByNameAndType
            (String name, INGREDIENT_TYPE type);
}
