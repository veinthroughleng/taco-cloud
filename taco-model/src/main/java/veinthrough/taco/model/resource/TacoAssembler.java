package veinthrough.taco.model.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import veinthrough.taco.model.Taco;

@Component
public class TacoAssembler
        implements ResourceAssembler<Taco, TacoResource> {
    private EntityLinks entityLinks;
    private IngredientAssembler ingredientAssembler;

    @Autowired
    public TacoAssembler(EntityLinks entityLinks, IngredientAssembler ingredientAssembler) {
        this.entityLinks = entityLinks;
        this.ingredientAssembler = ingredientAssembler;
    }

    @Override
    public TacoResource toResource(Taco taco) {
        TacoResource resource = new TacoResource(ingredientAssembler, taco);
        final LinkBuilder lb = entityLinks.linkForSingleResource(Taco.class, taco.getId());
        resource.add(lb.withSelfRel());
        return resource;
    }
}
