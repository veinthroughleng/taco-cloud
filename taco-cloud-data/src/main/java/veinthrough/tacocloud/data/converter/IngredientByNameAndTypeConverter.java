package veinthrough.tacocloud.data.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import veinthrough.tacocloud.data.IngredientRepository;
import veinthrough.tacocloud.model.Ingredient;
import static veinthrough.tacocloud.model.Ingredient.INGREDIENT_TYPE;

@Component
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
