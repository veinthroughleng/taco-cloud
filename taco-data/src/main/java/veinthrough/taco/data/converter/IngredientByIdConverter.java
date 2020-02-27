package veinthrough.taco.data.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import veinthrough.taco.data.IngredientRepository;
import veinthrough.taco.model.Ingredient;

@Component
@Profile({"mpa", "jpa-rest"})
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository repository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ingredient convert(@NonNull String id) {
        return repository.findById(id).orElse(null);
    }
}
