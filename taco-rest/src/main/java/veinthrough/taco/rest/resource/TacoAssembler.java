package veinthrough.taco.rest.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import veinthrough.taco.model.Taco;
import veinthrough.taco.rest.basic.DesignTacoController;

public class TacoAssembler
        extends ResourceAssemblerSupport<Taco, TacoResource> {

    TacoAssembler() {
        // "self" : {
        //          "href" : "http://localhost:8080/design/2"
        //        }
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
