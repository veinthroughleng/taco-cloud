package veinthrough.taco.rest.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import veinthrough.taco.data.TacoRepository;
import veinthrough.taco.data.converter.IngredientByNameAndTypeConverter;
import veinthrough.taco.model.Taco;
import veinthrough.taco.utils.MethodLog;
import java.util.Objects;
import java.util.stream.Collectors;

import static veinthrough.taco.utils.Constants.JSON;
import static veinthrough.taco.utils.Constants.PATH_DESIGN;

@RestController
@RequestMapping(path = PATH_DESIGN,
        produces = JSON,
        consumes = JSON)
@CrossOrigin(origins="*")
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
        log.info(MethodLog.log("DesignTacoController"));
        return tacoRepo.findById(id).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {

        //[DEBUG]
        final String METHOD = "postTaco";
        log.info(MethodLog.log(METHOD, "taco", taco.toString()));

        //id is absent in ingredients of taco returned from ui
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
        log.info(MethodLog.log(
                "postTaco", 0, "After assigned id",
                "taco", taco.toString()));

        Taco saved = tacoRepo.save(taco);

        //[DEBUG]
        log.info(MethodLog.log(METHOD, "saved", taco.toString()));
        return saved;
    }

}
