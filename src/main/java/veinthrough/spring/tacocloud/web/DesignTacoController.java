package veinthrough.spring.tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import veinthrough.spring.tacocloud.MethodLog;
import veinthrough.spring.tacocloud.data.IngredientRepository;
import veinthrough.spring.tacocloud.data.TacoRepository;
import veinthrough.spring.tacocloud.data.model.Ingredient;
import veinthrough.spring.tacocloud.data.model.Order;
import veinthrough.spring.tacocloud.data.model.Taco;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private static final String VIEW_DESIGN = "design";
    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    private void addIngredientsToModel(Model model) {
        Map<String, List<Ingredient>> typedIngredients = new HashMap<>();
        ingredientRepo.getTypedIngredients()
                .forEach((type, ingredients) ->
                        typedIngredients.put(type.toString().toLowerCase(), ingredients));
        model.addAllAttributes(typedIngredients);
    }
    @GetMapping
    public String showDesignForm(Model model) {
        addIngredientsToModel(model);
        return VIEW_DESIGN;
    }

    @PostMapping
    //the return name of Object Taco from template is default "taco" by lowercase
    //automatically call IngredientByIdConverter: string --> Ingredient
    public String processDesign(@Valid Taco design, Errors errors,
                                @ModelAttribute Order order, Model model) {
        //[DEBUG]
        final String METHOD = "processDesign";
        log.info(MethodLog.inLog(METHOD,
                "design", design.toString(),
                "order", order.toString()));

        if (errors.hasErrors()) {
            addIngredientsToModel(model);
            //[DEBUG]
            log.info(MethodLog.midLog(METHOD, 1,
                    "model", model.toString()));
            return VIEW_DESIGN;
        }
        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        //[DEBUG]
        log.info(MethodLog.outLog(METHOD,
                "design", design.toString(),
                "order", order.toString()));

        return "redirect:/orders/current";
    }
}
