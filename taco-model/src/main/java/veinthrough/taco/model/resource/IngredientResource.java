package veinthrough.taco.model.resource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import veinthrough.taco.model.Ingredient;

import static veinthrough.taco.model.Ingredient.INGREDIENT_TYPE;

@Relation(value = "ingredient", collectionRelation = "ingredients")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
// rest requires that entities have a no-arguments  constructor
public class IngredientResource extends ResourceSupport {
    @Getter
    private final String name;

    @Getter
    private final INGREDIENT_TYPE type;

    IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
