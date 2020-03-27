package veinthrough.taco.rest.resource;

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
import veinthrough.taco.model.Taco;
import veinthrough.taco.property.PageSizeProps;
import veinthrough.utils.MethodLog;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static veinthrough.utils.Constants.HAL_JSON;

@Slf4j
@RepositoryRestController
public class RecentTacosController {
    private TacoRepository tacoRepo;
    private PageSizeProps pageSizeProps;

    @Autowired
    public RecentTacosController(TacoRepository tacoRepo, PageSizeProps pageSizeProps) {
        //[DEBUG]
        log.info(MethodLog.inLog("RecentTacosController constructor",
                "pageSizeProps.taco", pageSizeProps.toString()));
        this.tacoRepo = tacoRepo;
        this.pageSizeProps = pageSizeProps;
    }

    @GetMapping(path="/tacos/recent", produces=HAL_JSON)
    public ResponseEntity<Resources<TacoResource>> recentTacos() {
        PageRequest page = PageRequest.of(
                0, pageSizeProps.getPageSizes().get("taco"), Sort.by("createdAt").descending());

        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        List<TacoResource> tacoResourcesList = new TacoAssembler().toResources(tacos);
        Resources<TacoResource> recentResources = new Resources<>(tacoResourcesList);

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