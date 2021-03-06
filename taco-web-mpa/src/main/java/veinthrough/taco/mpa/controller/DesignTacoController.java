package veinthrough.taco.mpa.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import veinthrough.taco.data.IngredientRepository;
import veinthrough.taco.data.TacoRepository;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.User;
import veinthrough.taco.utils.ListsToMap;
import veinthrough.taco.utils.MethodLog;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private static final String VIEW_DESIGN = "design";
    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo,
                                TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    //Add default filling-in
    public Order order(@AuthenticationPrincipal User user) {
        return new Order(user.getFullname(), user.getStreet(), user.getCity(), user.getState(), user.getZip());
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
    public String processDesign(@Valid @ModelAttribute Taco design, Errors errors,
                                @ModelAttribute Order order, Model model) {
        final String METHOD = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.debug(MethodLog.log(
                METHOD,
                1,
                "design", design.toString(),
                "order", order.toString()));

        if (errors.hasErrors()) {
            addIngredientsToModel(model);
            log.debug(MethodLog.log(
                    METHOD,
                    2,
                    "model", model.toString()));
            return VIEW_DESIGN;
        }

        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        log.debug(MethodLog.log(
                METHOD,
                3,
                "design", design.toString(),
                "order", order.toString()));

        return "redirect:/orders/current";
    }
}
