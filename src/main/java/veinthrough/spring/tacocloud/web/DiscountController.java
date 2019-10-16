package veinthrough.spring.tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import veinthrough.spring.tacocloud.util.MethodLog;
import veinthrough.spring.tacocloud.properties.DiscountProps;

@Controller
@Slf4j
@RequestMapping("/discount")
public class DiscountController {
    private DiscountProps discountProps;

    @Autowired
    public DiscountController(DiscountProps discountProps) {
        //[DEBUG]
        log.info(MethodLog.inLog("DiscountController constructor",
                "discountCodes", discountProps.getCodes().toString()));
        this.discountProps = discountProps;
    }

    @GetMapping
    public String displayDiscountCodes(Model model) {
        model.addAttribute("codes",
                discountProps.getCodes());
        return "discountList";
    }
}
