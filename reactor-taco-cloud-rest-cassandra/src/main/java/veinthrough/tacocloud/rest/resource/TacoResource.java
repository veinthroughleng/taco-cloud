package veinthrough.tacocloud.rest.resource;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import veinthrough.tacocloud.model.IngredientUDT;
import veinthrough.tacocloud.model.Taco;

import java.util.Date;
import java.util.List;


@Relation(value = "taco", collectionRelation = "tacos")
class TacoResource extends ResourceSupport {
    @Getter
    private final String name;

    @Getter
    private final Date createdAt;

    @Getter
    private final List<IngredientUDT> ingredients;

    TacoResource(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients = taco.getIngredients();
    }
}
