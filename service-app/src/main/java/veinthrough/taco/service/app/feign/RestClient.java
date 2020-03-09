package veinthrough.taco.service.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.User;
import veinthrough.taco.model.href.OrderHref;
import veinthrough.taco.model.href.TacoHref;
import veinthrough.taco.model.resource.TacoResource;

import static veinthrough.taco.utils.Constants.*;

@FeignClient(value = "service-rest",
//        fallback = FailedRestHandler.class,
//        configuration = FeignClientConfig.class,
        path = PATH_BASIC_REST)
public interface RestClient {
    // ingredients
    @GetMapping(PATH_INGREDIENTS + "/{id}")
    Resource<Ingredient> getIngredient(@PathVariable String id);

    @GetMapping(PATH_INGREDIENTS)
    Resources<Resource<Ingredient>> getAllIngredients();

    // tacos
    @GetMapping(PATH_TACOS + "/{id}" + PATH_INGREDIENTS)
    Resources<Resource<Ingredient>> getIngredientsOfTaco(@PathVariable Long id);

    @GetMapping(PATH_TACOS + "/{id}")
    Resource<Taco> getTaco(@PathVariable Long id);

    @GetMapping(PATH_TACOS + PATH_RECENT)
    Resources<TacoResource> recentTacos();

    @PostMapping(PATH_TACOS)
    Resource<Taco> postTaco(TacoHref taco);

    // users
    @GetMapping(PATH_USERS + "/{id}")
    Resource<User> getUser(@PathVariable Long id);

    @GetMapping(PATH_USERS + PATH_USERNAME + "/{name}")
    Resource<User> userByName(@PathVariable String name);

    @PostMapping(PATH_USERS)
    Resource<Taco> postUser(User user);

    // orders
    @GetMapping(PATH_ORDERS + "/{id}")
    Resource<Order> getOrder(@PathVariable Long id);

    @GetMapping(PATH_ORDERS + "/{id}" + PATH_TACOS)
    Resources<Resource<Taco>> getTacosOfOrder(@PathVariable Long id);

    @GetMapping(PATH_ORDERS + "/{id}" + PATH_USER)
    Resource<User> getUserOfOrder(@PathVariable Long id);

    @GetMapping(PATH_ORDERS + PATH_USER + "/{id}")
    Resources<Resource<Order>> ordersByUser(@PathVariable Long id);

    @PostMapping(PATH_ORDERS)
    Resource<Order> postOrder(OrderHref order);
}
