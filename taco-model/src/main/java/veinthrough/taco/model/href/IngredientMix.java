package veinthrough.taco.model.href;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Resource;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.resource.IngredientResource;

import static veinthrough.taco.model.href.Href.getIdFromHref;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class IngredientMix extends Ingredient implements Href {
    private String href;
    public IngredientMix(Resource<Ingredient> resource) {
        super(resource.getContent().getId(),
                resource.getContent().getName(),
                resource.getContent().getType());
        setHref(resource.getId().getHref());
        // id may be empty if didn't expose id
        if (getId() == null) {
            setId(getIdFromHref(href));
        }
    }

    IngredientMix(IngredientResource resource) {
        super(null, resource.getName(), resource.getType());
        setHref(resource.getId().getHref());
        setId(getIdFromHref(href));
    }
}
