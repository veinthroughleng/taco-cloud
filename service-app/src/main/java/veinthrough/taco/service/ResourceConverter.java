package veinthrough.taco.service;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.Taco;
import veinthrough.taco.model.href.IngredientMix;
import veinthrough.taco.model.href.OrderMix;
import veinthrough.taco.model.href.TacoMix;
import veinthrough.taco.model.resource.TacoResource;
import veinthrough.taco.service.feign.RestClient;

import java.util.List;
import java.util.stream.Collectors;

import static veinthrough.taco.model.href.Href.getIdFromHref;

// How to use generic to avoid every-converting???
class ResourceConverter {
    private RestClient rest;

    ResourceConverter(RestClient rest) {
        this.rest = rest;
    }

    TacoMix toTaco(Resource<Taco> resource) {
        // 1. get taco id
        String href = resource.getId().getHref();
        Long tacoId;
        Taco taco = resource.getContent();
        if (taco.getId() != null) {
            tacoId = resource.getContent().getId();
        } else {
            tacoId = Long.valueOf(getIdFromHref(href));
        }

        // 2. get ingredients of taco by rest client
        Resources<Resource<Ingredient>> ingredientResources = rest.getIngredientsOfTaco(tacoId);
        List<IngredientMix> ingredients = ingredientResources.getContent().stream()
                .map(IngredientMix::new)
                .collect(Collectors.toList());

        return new TacoMix(resource, ingredients);
    }

    TacoMix toTaco(TacoResource resource) {
        return new TacoMix(resource);
    }

    OrderMix toOrder(Resource<Order> resource) {
        // 1. get order id
        String href = resource.getId().getHref();
        Long orderId;
        Order order = resource.getContent();
        if (order.getId() != null) {
            orderId = resource.getContent().getId();
        } else {
            orderId = Long.valueOf(getIdFromHref(href));
        }

        // 2. get tacos of order by rest client
        Resources<Resource<Taco>> tacoResources = rest.getTacosOfOrder(orderId);
        List<TacoMix> tacos = tacoResources.getContent().stream()
                .map(this::toTaco)
                .collect(Collectors.toList());

        return new OrderMix(resource, tacos);
    }
}
