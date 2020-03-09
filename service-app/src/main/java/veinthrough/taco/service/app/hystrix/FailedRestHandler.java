package veinthrough.taco.service.app.hystrix;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.User;
import veinthrough.taco.model.href.OrderHref;
import veinthrough.taco.model.href.TacoHref;
import veinthrough.taco.model.resource.TacoResource;
import veinthrough.taco.service.app.feign.RestClient;
import veinthrough.taco.utils.MethodLog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static veinthrough.taco.model.DefaultIngredients.*;

@Slf4j
@Component
@Profile("feign-hystrix")
public class FailedRestHandler implements RestClient {
    private static final List<Ingredient> defaultIngredients = Lists.newArrayList(
            FLTO, COTO, GRBF, CARN, TMTO, LETC, CHED, JACK, SLSA, SRCR,
            // feign-hystrix
            VEIN);

    @Override
    public Resource<Ingredient> getIngredient(String id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get ingredient", "id", id));
        Optional<Ingredient> found = defaultIngredients.stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst();
        if (!found.isPresent()) {
            log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                    "Failed to get default ingredient", "id", id));
            return null;
        }
        Resource<Ingredient> ingredient = new Resource<>(found.get());
        ingredient.add(new Link("DEFAULT:ingredient/" + id, "self"));
        return ingredient;
    }

    @Override
    public Resources<Resource<Ingredient>> getAllIngredients() {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get ingredients"));
        Resources<Resource<Ingredient>> ingredients = new Resources<>(
                defaultIngredients.stream()
                        .map(ingredient -> {
                            Resource<Ingredient> resource = new Resource<>(ingredient);
                            resource.add(
                                    new Link("DEFAULT:ingredient/" + ingredient.getId(), "self"));
                            return resource;
                        })
                        .collect(Collectors.toList()));
        ingredients.add(new Link("DEFAULT:ingredients", "self"));
        return ingredients;
    }

    @Override
    public Resources<Resource<Ingredient>> getIngredientsOfTaco(Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get ingredients of taco", "id", id.toString()));
        return null;
    }

    @Override
    public Resource<Taco> getTaco(Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get taco", "id", id.toString()));
        return null;
    }

    @Override
    public Resource<Taco> postTaco(TacoHref taco) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to post taco", "taco", taco.toString()));
        return null;
    }

    @Override
    public Resource<User> getUser(Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get user", "user id", id.toString()));
        return null;
    }

    @Override
    public Resource<User> userByName(String name) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get user by name", "name", name));
        return null;
    }

    @Override
    public Resource<Taco> postUser(User user) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to post user", "user", user.toString()));
        return null;
    }

    @Override
    public Resources<TacoResource> recentTacos() {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get recent tacos"));
        return null;
    }

    @Override
    public Resource<Order> getOrder(Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get order", "order id", id.toString()));
        return null;
    }

    @Override
    public Resources<Resource<Taco>> getTacosOfOrder(Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get tacos of order", "order id", id.toString()));
        return null;
    }

    @Override
    public Resource<User> getUserOfOrder(Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get user of order", "order id", id.toString()));
        return null;
    }

    @Override
    public Resources<Resource<Order>> ordersByUser(@PathVariable Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get orders by current user", "user is", id.toString()));
        return null;
    }

    @Override
    public Resource<Order> postOrder(OrderHref order) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to post order", "order", order.toString()));
        return null;
    }
}
