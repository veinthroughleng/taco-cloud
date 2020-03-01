package veinthrough.taco.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.href.OrderHref;
import veinthrough.taco.model.href.TacoHref;
import veinthrough.taco.model.resource.TacoResource;
import veinthrough.taco.service.FailedRestHandler;

import static veinthrough.utils.Constants.*;

@FeignClient(value = "service-rest",
        fallback = FailedRestHandler.class,
        //configuration = FeignClientConfig.class,
        path = PATH_BASIC_REST)
public interface RestClient {
    @GetMapping(PATH_INGREDIENT + "/{id}")
    Resource<Ingredient> getIngredient(@PathVariable String id);

    @GetMapping(PATH_INGREDIENT)
    Resources<Resource<Ingredient>> getAllIngredients();

    @GetMapping(PATH_TACO + "/{id}" + PATH_INGREDIENT)
    Resources<Resource<Ingredient>> getIngredientsOfTaco(@PathVariable Long id);

    @GetMapping(PATH_TACO + "/{id}")
    Resource<Taco> getTaco(@PathVariable Long id);

    @PostMapping(PATH_TACO)
    Resource<Taco> postTaco(TacoHref taco);

    @GetMapping(PATH_TACO)
    Resources<Resource<Taco>> tacosOfUser();

    @GetMapping(PATH_TACO + PATH_RECENT)
    Resources<TacoResource> recentTacos();

    @GetMapping(PATH_ORDER + "/{id}")
    Resource<Order> getOrder(@PathVariable Long id);

    @GetMapping(PATH_ORDER + "/{id}" + PATH_TACO)
    Resources<Resource<Taco>> getTacosOfOrder(@PathVariable Long id);

    @GetMapping(PATH_ORDER)
    Resources<Resource<Order>> ordersOfUser();

    @PostMapping(PATH_ORDER)
    Resource<Order> postOrder(OrderHref order);
}
