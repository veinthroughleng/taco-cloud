package veinthrough.taco.service.rest.recent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import veinthrough.taco.model.Ingredient;

@Component
public class IngredientAssembler implements ResourceAssembler<Ingredient, IngredientResource> {
    private EntityLinks entityLinks;

    @Autowired
    public IngredientAssembler(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public IngredientResource toResource(Ingredient ingredient) {
        IngredientResource resource = new IngredientResource(ingredient);
        final LinkBuilder lb = entityLinks.linkForSingleResource(Ingredient.class, ingredient.getId());
        resource.add(lb.withSelfRel());
        return resource;
    }
}
