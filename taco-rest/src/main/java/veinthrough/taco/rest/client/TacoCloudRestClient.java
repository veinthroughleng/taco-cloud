package veinthrough.taco.rest.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.Taco;

import java.net.URI;
import java.util.List;

@Slf4j
@Deprecated
public class TacoCloudRestClient {
  private static final String REST_URL_BASIS = "http://localhost:8080/rest/";
  private static final String INGREDIENT_URL = "ingredients/";
  private static final String ID_ARG_URL = "{id}/";

  private RestTemplate rest;
  private Traverson traverson;

  public TacoCloudRestClient() {
    this.rest = new RestTemplate();
    this.traverson = new Traverson(
            URI.create(REST_URL_BASIS + "rest"), MediaTypes.HAL_JSON);
  }

  /*
   * Specify parameter as varargs argument
   */
  public Ingredient getIngredientById(String ingredientId) {
    return rest.getForObject(REST_URL_BASIS + INGREDIENT_URL + ID_ARG_URL,
                             Ingredient.class, ingredientId);
  }

  /*
   * Specify parameters with a map
   */
  // public Ingredient getIngredientById(String ingredientId) {
  //   Map<String, String> urlVariables = new HashMap<>();
  //   urlVariables.put("id", ingredientId);
  //   return rest.getForObject(REST_URL_BASIS + INGREDIENT_URL + ID_ARG_URL,
  //       Ingredient.class, urlVariables);
  // }

  /*
   * Request with URI instead of String
   */
  // public Ingredient getIngredientById(String ingredientId) {
  //   Map<String, String> urlVariables = new HashMap<>();
  //   urlVariables.put("id", ingredientId);
  //   URI url = UriComponentsBuilder
  //             .fromHttpUrl(REST_URL_BASIS + INGREDIENT_URL + ID_ARG_URL)
  //             .build(urlVariables);
  //   return rest.getForObject(url, Ingredient.class);
  // }

  /*
   * Use getForEntity() instead of getForObject()
   */
  // public Ingredient getIngredientById(String ingredientId) {
  //   ResponseEntity<Ingredient> responseEntity =
  //       rest.getForEntity(REST_URL_BASIS + INGREDIENT_URL + ID_ARG_URL,
  //           Ingredient.class, ingredientId);
  //   log.info("Fetched time: " +
  //           responseEntity.getHeaders().getDate());
  //   return responseEntity.getBody();
  // }

  public List<Ingredient> getAllIngredients() {
    return rest.exchange(REST_URL_BASIS + INGREDIENT_URL,
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {})
        .getBody();
  }

  public void updateIngredient(Ingredient ingredient) {
    rest.put(REST_URL_BASIS + INGREDIENT_URL + ID_ARG_URL,
          ingredient, ingredient.getId());
  }

  public Ingredient createIngredient(Ingredient ingredient) {
    return rest.postForObject(REST_URL_BASIS + INGREDIENT_URL,
        ingredient, Ingredient.class);
  }
  
  // public URI createIngredient(Ingredient ingredient) {
  //   return rest.postForLocation(REST_URL_BASIS + INGREDIENT_URL,
  //       ingredient, Ingredient.class);
  // }

  // public Ingredient createIngredient(Ingredient ingredient) {
  //   ResponseEntity<Ingredient> responseEntity =
  //          rest.postForEntity(REST_URL_BASIS + INGREDIENT_URL,
  //                             ingredient,
  //                             Ingredient.class);
  //   log.info("New resource created at " +
  //            responseEntity.getHeaders().getLocation());
  //   return responseEntity.getBody();
  // }
  
  public void deleteIngredient(Ingredient ingredient) {
    rest.delete(REST_URL_BASIS + INGREDIENT_URL + ID_ARG_URL,
        ingredient.getId());
  }

  //
  // Traverson with RestTemplate examples
  //

  public Iterable<Ingredient> getAllIngredientsWithTraverson() {
    ParameterizedTypeReference<Resources<Ingredient>> ingredientType =
        new ParameterizedTypeReference<Resources<Ingredient>>() {};

    Resources<Ingredient> ingredientRes =
        traverson
          .follow("ingredients")
          .toObject(ingredientType);
          
    return ingredientRes.getContent();
  }

  public Ingredient addIngredient(Ingredient ingredient) {
    String ingredientsUrl = traverson
        .follow("ingredients")
        .asLink()
        .getHref();
    return rest.postForObject(ingredientsUrl,
                              ingredient,
                              Ingredient.class);
  }

  public Iterable<Taco> getRecentTacosWithTraverson() {
    ParameterizedTypeReference<Resources<Taco>> tacoType =
        new ParameterizedTypeReference<Resources<Taco>>() {};

    Resources<Taco> tacoRes =
        traverson
          .follow("tacos")
          .follow("recents")
          .toObject(tacoType);

      // Alternatively, list the two paths in the same call to follow()
//    Resources<Taco> tacoRes =
//        traverson
//          .follow("tacos", "recents")
//          .toObject(tacoType);

    return tacoRes.getContent();
  }

}
