package veinthrough.taco.model.href;

import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
// only attributes, no id/placedAt
public class TacoHref {
    private String name;
    private List<String> ingredients;

    public TacoHref(TacoMix mix) {
        setName(mix.getName());
        setIngredients(
                mix.getIngredients().stream()
                        .map(IngredientMix::getHref)
                        .collect(Collectors.toList()));
    }
}
