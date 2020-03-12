package veinthrough.taco.service.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.User;
import veinthrough.taco.model.href.*;
import veinthrough.taco.service.app.feign.RestClient;
import veinthrough.taco.utils.MethodLog;

import java.util.List;
import java.util.stream.Collectors;

import static veinthrough.taco.utils.Constants.*;

@RestController
@RequestMapping(path = PATH_BASIC_REST, produces = JSON)
@CrossOrigin(origins = "*")
@Slf4j
public class SPAController {
    private RestClient rest;
    private ResourceConverter converter;

    @Autowired
    public SPAController(RestClient rest) {
        this.rest = rest;
        this.converter = new ResourceConverter(rest);
    }

    @GetMapping(path = PATH_INGREDIENTS + "/{id}")
    public IngredientMix getIngredient(@PathVariable String id) {
        return new IngredientMix(rest.getIngredient(id));
    }

    @GetMapping(path = PATH_INGREDIENTS)
    public List<IngredientMix> getAllIngredients() {
        return rest.getAllIngredients().getContent().stream()
                // has exposed id
                .map(IngredientMix::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = PATH_TACOS + "/{id}")
    public TacoMix getTaco(@PathVariable Long id) {
        return converter.toTaco(rest.getTaco(id));
    }

    @GetMapping(path = PATH_TACOS + PATH_RECENT)
    public Iterable<TacoMix> recentTacos() {
        return rest.recentTacos().getContent().stream()
                .map(converter::toTaco)
                .collect(Collectors.toList());
    }

    @PostMapping(path = PATH_TACOS)
    @ResponseStatus(HttpStatus.CREATED)
    public TacoMix postTaco(@RequestBody TacoMix taco, @AuthenticationPrincipal User user) {
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "authenticated user", user.toString()));

        TacoMix tacoPosted = converter.toTaco(
                rest.postTaco(
                        new TacoHref(taco)));
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "taco to be posted", taco.toString(),
                "taco posted", tacoPosted.toString()));
        return tacoPosted;
    }

    @GetMapping(path = PATH_USER_INFO)
    public UserMix userInfo(@AuthenticationPrincipal UserMix user) {
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "authenticated user", user.toString()));
        // delete password for security purpose
        return UserMix.userInfo(user);
    }

    @GetMapping(path = PATH_ORDERS)
    public Iterable<OrderMix> OrdersOfUser(@AuthenticationPrincipal UserMix user) {
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "authenticated user", user.toString()));

        Iterable<OrderMix> orders = rest.ordersByUser(user.getId()).getContent().stream()
                .map(converter::toOrder)
                .collect(Collectors.toList());
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "user", user.getFullname(),
                "orders of user", orders.toString()));
        return orders;
    }

    @PostMapping(path = PATH_ORDERS)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderMix postOrder(@RequestBody OrderMix order, @AuthenticationPrincipal UserMix user) {
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "authenticated user", user.toString()));

        order.setUser(new UserMix(rest.getUser(user.getId())));
        // [DEBUG]
        OrderHref toBePost = new OrderHref(order);
        Resource<Order> posted = rest.postOrder(toBePost);
        OrderMix orderPosted = converter.toOrder(posted);
//        OrderMix orderPosted = converter.toOrder(
//                rest.postOrder(
//                        new OrderHref(order)));
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "order to be posted", order.toString(),
                "order posted", orderPosted.toString()));
        return orderPosted;
    }
}
