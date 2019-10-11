package veinthrough.spring.tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import veinthrough.spring.tacocloud.MethodLog;
import veinthrough.spring.tacocloud.data.OrderRepository;
import veinthrough.spring.tacocloud.data.model.Order;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
    private OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors,
                               SessionStatus sessionStatus, Model model) {
        //[DEBUG]
        final String METHOD = "processorOrder";
        log.info(MethodLog.inLog(METHOD,
                "order", order.toString(),
                "errors", errors.toString(),
                "model", model.toString()));

        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepo.save(order);
        sessionStatus.setComplete();

        //[DEBUG]
        log.info(MethodLog.outLog(METHOD,
                "order", order.toString(),
                "errors", errors.toString(),
                "model", model.toString()));
        return "redirect:/";
    }
}
