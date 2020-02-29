package veinthrough.taco.mpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import veinthrough.taco.data.OrderRepository;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.User;
import veinthrough.taco.property.PageSizeProps;
import veinthrough.utils.MethodLog;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
    private OrderRepository orderRepo;
    private PageSizeProps pageSizeProps;

    @Autowired
    public OrderController(OrderRepository orderRepo, PageSizeProps pageSizeProps) {
        //[DEBUG]
        log.info(MethodLog.log("OrderController constructor",
                "pageSizeProps.order", pageSizeProps.getPageSizes().get("order").toString()));
        this.orderRepo = orderRepo;
        this.pageSizeProps = pageSizeProps;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute Order order, Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user,
                               Model model) {
        //[DEBUG]
        final String METHOD = "processorOrder";
        log.info(MethodLog.log(METHOD,
                "order", order.toString(),
                "errors", errors.toString(),
                "model", model.toString()));

        if (errors.hasErrors()) {
            return "orderForm";
        }

        //add belonging user
        order.setUser(user);
        orderRepo.save(order);
        sessionStatus.setComplete();

        //[DEBUG]
        log.info(MethodLog.log(METHOD,
                "order", order.toString(),
                "errors", errors.toString(),
                "model", model.toString()));
        return "redirect:/";
    }

    @GetMapping
    public String ordersOfUser(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, pageSizeProps.getPageSizes().get("order"));
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
}
