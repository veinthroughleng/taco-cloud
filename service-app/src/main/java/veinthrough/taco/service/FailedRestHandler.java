package veinthrough.taco.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.href.OrderHref;
import veinthrough.taco.model.href.TacoHref;
import veinthrough.taco.model.resource.TacoResource;
import veinthrough.taco.service.feign.RestClient;
import veinthrough.utils.MethodLog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static veinthrough.taco.service.DefaultIngredients.*;

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
                "Failed to get ingredient.", "id", id));
        Optional<Ingredient> found = defaultIngredients.stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst();
        if (!found.isPresent()) {
            log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                    "Failed to get default ingredient.", "id", id));
            return null;
        }
        Resource<Ingredient> ingredient = new Resource<>(found.get());
        ingredient.add(new Link("DEFAULT:ingredient/" + id, "self"));
        return ingredient;
    }

    @Override
    public Resources<Resource<Ingredient>> getAllIngredients() {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get ingredients."));
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
                "Failed to get ingredients of taco.", "id", id.toString()));
        return null;
    }

    @Override
    public Resource<Taco> getTaco(Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get taco.", "id", id.toString()));
        return null;
    }

    @Override
    public Resource<Taco> postTaco(TacoHref taco) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to post taco.", "taco", taco.toString()));
        return null;
    }

    @Override
    public Resources<Resource<Taco>> tacosOfUser() {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get tacos of current user."));
        return null;
    }

    @Override
    public Resources<TacoResource> recentTacos() {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get recent tacos."));
        return null;
    }

    @Override
    public Resource<Order> getOrder(Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get order.", "id", id.toString()));
        return null;
    }

    @Override
    public Resources<Resource<Taco>> getTacosOfOrder(Long id) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get tacos of order.", "id", id.toString()));
        return null;
    }

    @Override
    public Resources<Resource<Order>> ordersOfUser() {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to get orders of current user."));
        return null;
    }

    @Override
    public Resource<Order> postOrder(OrderHref order) {
        log.warn(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "Failed to post order.", "order", order.toString()));
        return null;
    }
}
