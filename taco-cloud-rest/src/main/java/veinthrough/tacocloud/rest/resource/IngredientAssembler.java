package veinthrough.tacocloud.rest.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import veinthrough.tacocloud.model.Ingredient;

public class IngredientAssembler extends
        ResourceAssemblerSupport<Ingredient, IngredientResource> {

    public IngredientAssembler() {
        super(IngredientController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource toResource(Ingredient ingredient) {
        return createResourceWithId(ingredient.getId(), ingredient);
    }

    @Override
    protected IngredientResource instantiateResource(Ingredient ingredient) {
        return new IngredientResource(ingredient);
    }
}
