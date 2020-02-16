package veinthrough.tacocloud.data.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import veinthrough.tacocloud.data.IngredientRepository;
import veinthrough.tacocloud.model.Ingredient;

@Component
public class IngredientByNameAndTypeConverter {

    private IngredientRepository repository;

    @Autowired
    public IngredientByNameAndTypeConverter(IngredientRepository repository) {
        this.repository = repository;
    }

    public Mono<Ingredient> convert(@NonNull String name,
                                    @NonNull Ingredient.INGREDIENT_TYPE type) {
        return repository.findByNameAndType(name, type);
    }
}
