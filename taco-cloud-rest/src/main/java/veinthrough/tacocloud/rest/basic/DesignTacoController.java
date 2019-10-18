package veinthrough.tacocloud.rest.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import veinthrough.tacocloud.data.TacoRepository;
import veinthrough.tacocloud.model.Taco;
import veinthrough.utils.MethodLog;

import static veinthrough.utils.Constants.JSON;
import static veinthrough.utils.Constants.PATH_DESIGN;

@RestController
@RequestMapping(path = PATH_DESIGN,
        produces = JSON,
        consumes = JSON)
@CrossOrigin(origins = "*")
@Slf4j
public class DesignTacoController {
    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/{id}")
    public Taco tacoById(@PathVariable("id") Long id) {
        //[DEBUG]
        log.info(MethodLog.inLog("DesignTacoController"));
        return tacoRepo.findById(id).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

}
