package veinthrough.spring.tacocloud.data;

import com.google.common.collect.Lists;
import veinthrough.spring.tacocloud.data.model.Ingredient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractIngredientRepository implements IngredientRepository {
    @Override
    public List<Ingredient> getIngredients() {
        return Lists.newArrayList(getAll());
    }

    @Override
    public Map<Ingredient.INGREDIENT_TYPE, List<Ingredient>> getTypedIngredients() {
        return getIngredients().stream()
                .collect(Collectors.groupingBy(Ingredient::getType));
    }
}
