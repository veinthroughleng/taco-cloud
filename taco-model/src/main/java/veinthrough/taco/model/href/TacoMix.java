package veinthrough.taco.model.href;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Resource;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.resource.TacoResource;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static veinthrough.taco.model.href.Href.getIdFromHref;

@Data
@NoArgsConstructor
public class TacoMix implements Href {
    private Long id;
    private List<IngredientMix> ingredients;
    private String href;

    // attributes
    private String name;
    private Date createdAt;

    public TacoMix(TacoResource resource) {
        this.name = resource.getName();
        this.createdAt = resource.getCreatedAt();

        // 1. set href
        setHref(resource.getId().getHref());

        // 2. set id
        setId(Long.valueOf(getIdFromHref(href)));

        // 3. set ingredients
        setIngredients(
                resource.getIngredients().stream()
                        .map(IngredientMix::new)
                        .collect(Collectors.toList()));
    }

    public TacoMix(Resource<Taco> resource, List<IngredientMix> ingredients) {
        this.name = resource.getContent().getName();
        this.createdAt = resource.getContent().getCreatedAt();

        // 1. set href
        setHref(resource.getId().getHref());

        // 2. set id
        if (resource.getContent().getId() != null) {
            setId(resource.getContent().getId());
        } else {
            setId(Long.valueOf(getIdFromHref(href)));
        }

        // 3. set ingredients
        setIngredients(ingredients);
    }
}
