package veinthrough.taco.rest.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;
import veinthrough.taco.data.TacoRepository;
import veinthrough.taco.property.PageSizeProps;
import veinthrough.utils.MethodLog;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static veinthrough.utils.Constants.HAL_JSON;

@RepositoryRestController
@Slf4j
public class RecentTacosController {
    private TacoRepository tacoRepo;
    private PageSizeProps pageSizeProps;

    public RecentTacosController(TacoRepository tacoRepo, PageSizeProps pageSizeProps) {
        //[DEBUG]
        log.info(MethodLog.inLog("RecentTacosController constructor",
                "pageSizeProps.taco", pageSizeProps.toString()));
        this.tacoRepo = tacoRepo;
        this.pageSizeProps = pageSizeProps;
    }

    @GetMapping(path="/tacos/recent", produces=HAL_JSON)
    public Mono<ResponseEntity<Resources<TacoResource>>> recentTacos() {
        return tacoRepo.findAll()
                .take(pageSizeProps.getPageSizes().get("taco"))
                .map(taco -> new TacoAssembler().toResource(taco))
                .buffer()
                .next()
                .map(tacos -> {
                    Resources<TacoResource> resources = new Resources<>(tacos);
                    resources.add(
                            linkTo(methodOn(RecentTacosController.class).recentTacos())
                                    .withRel("recents"));
                    return resources;
                })
                .map(resources -> new ResponseEntity<>(resources, HttpStatus.OK));
    }
}
