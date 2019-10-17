package veinthrough.tacocloud.web.controller;

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
import veinthrough.tacocloud.data.OrderRepository;
import veinthrough.tacocloud.model.Order;
import veinthrough.tacocloud.model.User;
import veinthrough.tacocloud.web.properties.OrderProps;
import veinthrough.utils.MethodLog;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
    private OrderRepository orderRepo;
    private OrderProps orderProps;

    @Autowired
    public OrderController(OrderRepository orderRepo, OrderProps orderProps) {
        //[DEBUG]
        log.info(MethodLog.inLog("OrderController constructor",
                "orderProps", orderProps.getPageSize().toString()));
        this.orderRepo = orderRepo;
        this.orderProps = orderProps;
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
        log.info(MethodLog.inLog(METHOD,
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
        log.info(MethodLog.outLog(METHOD,
                "order", order.toString(),
                "errors", errors.toString(),
                "model", model.toString()));
        return "redirect:/";
    }

    @GetMapping
    public String ordersOfUser(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
}
