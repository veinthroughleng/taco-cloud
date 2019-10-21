package veinthrough.tacocloud.rest.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import veinthrough.tacocloud.data.TacoRepository;
import veinthrough.tacocloud.data.converter.IngredientByNameAndTypeConverter;
import veinthrough.tacocloud.model.Taco;
import veinthrough.utils.MethodLog;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private IngredientByNameAndTypeConverter ingredientConverter;

    @Autowired
    public DesignTacoController
            (TacoRepository tacoRepo,
             IngredientByNameAndTypeConverter ingredientConverter) {
        this.tacoRepo = tacoRepo;
        this.ingredientConverter = ingredientConverter;
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

        //[DEBUG]
        final String METHOD = "postTaco";
        log.info(MethodLog.inLog(METHOD, "taco", taco.toString()));

        //id is absent from ingredients of taco returned from ui
        taco.setIngredients(
                taco.getIngredients().stream()
                        .map(ingredient ->
                                ingredient.getId() == null ?
                                        ingredientConverter.convert(
                                                ingredient.getName(), ingredient.getType()) :
                                        ingredient)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));

        //[DEBUG]
        log.info(MethodLog.midLog(
                "postTaco", "After assigned id", 0,
                "taco", taco.toString()));

        Taco saved = tacoRepo.save(taco);

        //[DEBUG]
        log.info(MethodLog.outLog(METHOD, "saved", taco.toString()));
        return saved;
    }

}
