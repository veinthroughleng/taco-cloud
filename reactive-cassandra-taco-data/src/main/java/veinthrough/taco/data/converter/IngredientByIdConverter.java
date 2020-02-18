package veinthrough.taco.data.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import veinthrough.taco.data.IngredientRepository;
import veinthrough.taco.model.Ingredient;

@Component
public class IngredientByIdConverter implements Converter<String, Mono<Ingredient>> {

    private IngredientRepository repository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Ingredient> convert(@NonNull String id) {
        return repository.findById(id);
    }
}
