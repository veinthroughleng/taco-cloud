package veinthrough.taco.mpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import veinthrough.taco.property.TacoProps;
import veinthrough.utils.MethodLog;

@Controller
@Slf4j
@RequestMapping("/discount")
public class DiscountController {
    private TacoProps tacoProps;

    @Autowired
    public DiscountController(TacoProps tacoProps) {
        //[DEBUG]
        log.info(MethodLog.log("DiscountController constructor",
                "discountCodes", tacoProps.getDiscountCodes().toString()));
        this.tacoProps = tacoProps;
    }

    @GetMapping
    public String displayDiscountCodes(Model model) {
        model.addAttribute("codes",
                tacoProps.getDiscountCodes());
        return "discountList";
    }
}
