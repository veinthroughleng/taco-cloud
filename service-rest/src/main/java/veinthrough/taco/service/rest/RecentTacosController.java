package veinthrough.taco.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import veinthrough.taco.data.TacoRepository;
import veinthrough.taco.model.resource.TacoAssembler;
import veinthrough.taco.model.resource.TacoResource;
import veinthrough.taco.property.TacoProps;
import veinthrough.taco.utils.MethodLog;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static veinthrough.taco.utils.Constants.HAL_JSON;

@Slf4j
@RepositoryRestController
public class RecentTacosController {
    private TacoRepository tacoRepo;
    private TacoProps tacoProps;
    private TacoAssembler tacoAssembler;

    @Autowired
    public RecentTacosController(TacoRepository tacoRepo, TacoProps tacoProps, TacoAssembler tacoAssembler) {
        this.tacoAssembler = tacoAssembler;
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "tacoProps", tacoProps.toString()));
        this.tacoRepo = tacoRepo;
        this.tacoProps = tacoProps;
    }

    @GetMapping(path = "/tacos/recent", produces = HAL_JSON)
    public ResponseEntity<Resources<TacoResource>> recentTacos() {
        PageRequest page = PageRequest.of(
                0, tacoProps.getPageSizes().get("taco"), Sort.by("createdAt").descending());

        Resources<TacoResource> recentResources = new Resources<>(
                tacoRepo.findAll(page).getContent().stream()
                        .map(tacoAssembler::toResource)
                        .collect(Collectors.toList()));

        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "recent taco resources gotten", recentResources.toString()));

        //"_links": {
        //    "recents": {
        //        "href": "http://localhost:8080/tacos/recent"
        //    }
        recentResources.add(
                linkTo(methodOn(RecentTacosController.class).recentTacos())
                        .withRel("recents"));

        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }
}
