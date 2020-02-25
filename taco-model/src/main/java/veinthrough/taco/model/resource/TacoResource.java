package veinthrough.taco.model.resource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import veinthrough.taco.model.Taco;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Relation(value = "taco", collectionRelation = "tacos")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
// rest requires that entities have a no-arguments  constructor
public class TacoResource extends ResourceSupport {
    @Getter
    private final String name;

    @Getter
    private final Date createdAt;

    @Getter
    private final List<IngredientResource> ingredients;

    TacoResource(IngredientAssembler ingredientAssembler, Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients = taco.getIngredients().stream()
                .map(ingredientAssembler::toResource)
                .collect(Collectors.toList());
    }
}
