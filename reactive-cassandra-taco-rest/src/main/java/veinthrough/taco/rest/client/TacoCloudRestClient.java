package veinthrough.taco.rest.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import veinthrough.taco.model.Ingredient;

import java.time.Duration;

@Slf4j
@Deprecated
public class TacoCloudRestClient {
    private static final String REST_URL_BASIS = "http://localhost:8080/rest";
    private static final String INGREDIENT_URL = "/ingredients";
    private static final String ID_ARG_URL = "/{id}";
    private WebClient client;

    public TacoCloudRestClient() {
        this.client = WebClient.create(REST_URL_BASIS);
    }


    public Flux<Ingredient> getAllIngredients() {
        return client.get()
                .uri(INGREDIENT_URL)
                .retrieve()
                .bodyToFlux(Ingredient.class);
    }

    public Mono<Ingredient> getIngredientById(String id) {
        return client.get()
                .uri(INGREDIENT_URL + ID_ARG_URL, id)
                .retrieve()
                .bodyToMono(Ingredient.class);
    }

    public Mono<Ingredient> createIngredient(Mono<Ingredient> ingredient) {
        return client.post()
                .uri(INGREDIENT_URL + ID_ARG_URL)
                .body(ingredient, Ingredient.class)
                .retrieve()
                .bodyToMono(Ingredient.class);
    }

    public Mono<Void> updateIngredient(Ingredient ingredient) {
        return client.put()
                .uri(INGREDIENT_URL + ID_ARG_URL, ingredient.getId())
                .syncBody(ingredient)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> patchIngredient(String id, Ingredient ingredient) {
        return client.patch()
                .uri(INGREDIENT_URL + ID_ARG_URL, id)
                .syncBody(ingredient)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteIngredient(String id) {
        return client.delete()
                .uri(INGREDIENT_URL + ID_ARG_URL, id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    private void testSubscribe() {
        getAllIngredients()
                .timeout(Duration.ofSeconds(2))
                .subscribe(
                        ingredient -> {
                        },
                        error -> {
                            //maybe timeout error
                        }
                );
    }
}
