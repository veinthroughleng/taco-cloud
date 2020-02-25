package veinthrough.taco.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.href.OrderHref;
import veinthrough.taco.model.href.TacoHref;
import veinthrough.taco.model.resource.TacoResource;

import static veinthrough.utils.Constants.PATH_BASIC_REST;

@FeignClient("service-rest")
@RequestMapping(path = PATH_BASIC_REST, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface RestClient {
    @GetMapping("/ingredients/{id}")
    Ingredient getIngredient(@PathVariable("id") String id);

    @GetMapping("/ingredients")
    Resources<Resource<Ingredient>> getAllIngredients();

    @GetMapping("/tacos/{id}/ingredients")
    Resources<Resource<Ingredient>> getIngredientsOfTaco(@PathVariable Long id);

    @GetMapping("/tacos/{id}")
    Resource<Taco> getTaco(@PathVariable Long id);

    @PostMapping("/tacos")
    Resource<Taco> postTaco(TacoHref taco);

    @GetMapping("/tacos")
    Resources<Resource<Taco>> tacosOfUser();

    @GetMapping("/tacos/recent")
    Resources<TacoResource> recentTacos();

    @GetMapping("/orders/{id}/tacos")
    Resources<Resource<Taco>> getTacosOfOrder(@PathVariable Long id);

    @GetMapping("/orders")
    Resources<Resource<Order>> ordersOfUser();

    @PostMapping("/orders")
    Resource<Order> postOrder(OrderHref order);
}
