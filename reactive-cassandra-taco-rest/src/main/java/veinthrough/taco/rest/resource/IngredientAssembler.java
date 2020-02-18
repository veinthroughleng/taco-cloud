package veinthrough.taco.rest.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import veinthrough.taco.model.Ingredient;

public class IngredientAssembler extends
        ResourceAssemblerSupport<Ingredient, IngredientResource> {

    IngredientAssembler() {
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
