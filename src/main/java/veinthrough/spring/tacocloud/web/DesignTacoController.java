package veinthrough.spring.tacocloud.web;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import veinthrough.spring.tacocloud.MethodLog;
import veinthrough.spring.tacocloud.data.IngredientRepository;
import veinthrough.spring.tacocloud.data.IngredientsInitializer;
import veinthrough.spring.tacocloud.data.TacoRepository;
import veinthrough.spring.tacocloud.data.model.Order;
import veinthrough.spring.tacocloud.data.model.Taco;
import veinthrough.spring.tacocloud.data.model.converter.ListsToMap;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private static final String VIEW_DESIGN = "design";
    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;
    private IngredientsInitializer ingredientsInit;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo,
                                TacoRepository designRepo,
                                IngredientsInitializer ingredientsInit) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
        this.ingredientsInit = ingredientsInit;
        this.ingredientsInit.initialize();
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
        final boolean LOWER_CASE = true;
        model.addAllAttributes(
                ListsToMap.toMap(
                        Lists.newArrayList(ingredientRepo.findAll()),
                        ingredient -> ingredient.getTypeString(LOWER_CASE)));
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
