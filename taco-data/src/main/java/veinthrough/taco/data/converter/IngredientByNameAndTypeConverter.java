package veinthrough.taco.data.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import veinthrough.taco.data.IngredientRepository;
import veinthrough.taco.model.Ingredient;
import static veinthrough.taco.model.Ingredient.INGREDIENT_TYPE;

@Component
@Profile("jpa-rest")
public class IngredientByNameAndTypeConverter {

    private IngredientRepository repository;

    @Autowired
    public IngredientByNameAndTypeConverter(IngredientRepository repository) {
        this.repository = repository;
    }

    public Ingredient convert(@NonNull String name,
                              @NonNull INGREDIENT_TYPE type) {
        return repository.findByNameAndType(name, type);
    }
}
