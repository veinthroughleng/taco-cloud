package veinthrough.taco.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import veinthrough.taco.data.TacoRepository;
import veinthrough.taco.model.Taco;
import veinthrough.taco.rest.resource.RecentTacosController;

import static veinthrough.taco.rest.TacoSupplier.getTaco;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers= RecentTacosController.class)
@ContextConfiguration(classes=RecentTacosController.class)
public class RecentTacosControllerWebTest {
  @Autowired
  private WebTestClient testClient;
  
  @MockBean
  private TacoRepository tacoRepo;

  @Test
  public void shouldReturnRecentTacos() {
    Taco[] tacos = {
        getTaco(1L), getTaco(2L), getTaco(3L), getTaco(4L),
        getTaco(5L), getTaco(6L), getTaco(7L), getTaco(8L),
        getTaco(9L), getTaco(10L), getTaco(11L), getTaco(12L)};

    Mockito.when(tacoRepo.findAll())
      .thenReturn(Flux.fromArray(tacos));
    
    testClient.get().uri("/tacos/recent")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      // expectBody()/ expectBodyList()
      .expectBodyList(Taco.class).contains(tacos);
  }
}
