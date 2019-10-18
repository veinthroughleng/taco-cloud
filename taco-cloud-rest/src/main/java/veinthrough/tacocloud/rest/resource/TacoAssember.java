package veinthrough.tacocloud.rest.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import veinthrough.tacocloud.model.Taco;
import veinthrough.tacocloud.rest.basic.DesignTacoController;

public class TacoAssember
        extends ResourceAssemblerSupport<Taco, TacoResource> {

    public TacoAssember() {
        super(DesignTacoController.class, TacoResource.class);
    }

    @Override
    public TacoResource toResource(Taco taco) {
        return createResourceWithId(taco.getId(), taco);
    }

    @Override
    protected TacoResource instantiateResource(Taco taco) {
        return new TacoResource(taco);
    }
}
