package veinthrough.taco.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.href.*;
import veinthrough.taco.service.feign.RestClient;

import java.util.List;
import java.util.stream.Collectors;

import static veinthrough.utils.Constants.*;

@RestController
@RequestMapping(produces = JSON)
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

    @GetMapping(path = PATH_INGREDIENT)
    public List<IngredientMix> getAllIngredients() {
        return rest.getAllIngredients().getContent().stream()
                // has exposed id
                .map(IngredientMix::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = PATH_TACO + "/{id}")
    public TacoMix getTaco(@PathVariable Long id) {
        return converter.toTaco(rest.getTaco(id));
    }

    @GetMapping(path = PATH_TACO + "/recent")
    public Iterable<TacoMix> recentTacos() {
        return rest.recentTacos().getContent().stream()
                .map(converter::toTaco)
                .collect(Collectors.toList());
    }

    @GetMapping(path = PATH_TACO)
    public Iterable<TacoMix> tacosOfUser() {
        return rest.tacosOfUser().getContent().stream()
                .map(converter::toTaco)
                .collect(Collectors.toList());
    }

    @PostMapping(path = PATH_TACO)
    @ResponseStatus(HttpStatus.CREATED)
    public TacoMix postTaco(@RequestBody TacoMix taco) {
        return converter.toTaco(
                rest.postTaco(
                        new TacoHref(taco)));
    }

    @PostMapping(path = PATH_ORDER)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderMix postOrder(@RequestBody OrderMix order) {
        OrderHref href = new OrderHref(order);
        Resource<Order> result = rest.postOrder(href);
        OrderMix mix = converter.toOrder(result);
        return mix;
//        return converter.toOrder(
//                rest.postOrder(
//                        new OrderHref(order)));
    }
}
