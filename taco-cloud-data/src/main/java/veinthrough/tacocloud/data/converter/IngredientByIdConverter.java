package veinthrough.tacocloud.data.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import veinthrough.tacocloud.data.IngredientRepository;
import veinthrough.tacocloud.model.Ingredient;

import java.util.Optional;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository repository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ingredient convert(String id) {
        Optional<Ingredient> optionalIngredient = repository.findById(id);
        return optionalIngredient.orElse(null);
    }
}
