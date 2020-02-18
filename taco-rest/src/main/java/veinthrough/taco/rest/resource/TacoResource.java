package veinthrough.taco.rest.resource;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import veinthrough.taco.model.Taco;

import java.util.Date;
import java.util.List;


@Relation(value = "taco", collectionRelation = "tacos")
class TacoResource extends ResourceSupport {
    private static final IngredientAssembler
            ingredientAssembler = new IngredientAssembler();
    @Getter
    private final String name;

    @Getter
    private final Date createdAt;

    @Getter
    private final List<IngredientResource> ingredients;

    TacoResource(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients =
                ingredientAssembler.toResources(taco.getIngredients());
    }
}
