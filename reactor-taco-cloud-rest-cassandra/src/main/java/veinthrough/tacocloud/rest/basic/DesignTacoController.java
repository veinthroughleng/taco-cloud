package veinthrough.tacocloud.rest.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import veinthrough.tacocloud.data.TacoRepository;
import veinthrough.tacocloud.model.Taco;
import veinthrough.utils.MethodLog;

import java.util.UUID;

import static veinthrough.utils.Constants.JSON;
import static veinthrough.utils.Constants.PATH_DESIGN;

@RestController
@RequestMapping(path = PATH_DESIGN,
        produces = JSON,
        consumes = JSON)
@CrossOrigin
@Slf4j
public class DesignTacoController {
    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController
            (TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/{id}")
    public Mono<Taco> tacoById(@PathVariable("id") UUID id) {
        // tacoRepo returns:
        // reactive: Mono<Taco>
        // Non-reactive: Optional<Taco>
        return tacoRepo.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Mono<Taco> taco) {

        //[DEBUG]
        log.info(MethodLog.inLog(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "taco", taco.toString()));

        return taco.flatMap(tacoRepo::save);
    }
}
