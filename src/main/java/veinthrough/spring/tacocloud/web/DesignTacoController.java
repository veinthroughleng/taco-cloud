package veinthrough.spring.tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import veinthrough.spring.tacocloud.MethodLog;
import veinthrough.spring.tacocloud.data.Ingredients;
import veinthrough.spring.tacocloud.data.model.Ingredient;
import veinthrough.spring.tacocloud.data.model.Taco;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("design")
public class DesignTacoController {
    private static final String VIEW_DESIGN = "design";
    private static final String MODEL_KEY_DESIGN = "design";

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Map<String, List<Ingredient>> typedIngredients = new HashMap<>();
        Ingredients.getTypedIngredients()
                .forEach((type, ingredients) ->
                        typedIngredients.put(type.toString().toLowerCase(), ingredients));
        model.addAllAttributes(typedIngredients);
    }

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute(MODEL_KEY_DESIGN, new Taco());
        return VIEW_DESIGN;
    }

    @PostMapping
    //without @ModelAttribute("design"), the return name of Object Taco from template is default "taco" rather than "design"
    //taco=Taco(ingredients=[FLTO, CHED], name=vein)
    //design=Taco(ingredients=[FLTO, CHED], name=vein)
    public String processDesign(@Valid @ModelAttribute(MODEL_KEY_DESIGN) Taco design, Errors errors, Model model) {
        //[DEBUG][3]
        final String METHOD = "processDesign";
        log.info(MethodLog.inLog(METHOD, "design", design.toString()));
        log.info(MethodLog.inLog(METHOD, "errors", errors.toString()));
        log.info(MethodLog.inLog(METHOD, "model", model.toString()));
        if (errors.hasErrors()) {
            return VIEW_DESIGN;
        }
        //[DEBUG][3]
        log.info(MethodLog.outLog(METHOD, "design", design.toString()));
        log.info(MethodLog.outLog(METHOD, "errors", errors.toString()));
        log.info(MethodLog.outLog(METHOD, "model", model.toString()));
        return "redirect:/orders/current";
    }
}
