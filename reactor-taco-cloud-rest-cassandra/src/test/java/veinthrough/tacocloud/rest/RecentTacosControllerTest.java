package veinthrough.tacocloud.rest;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import veinthrough.tacocloud.data.TacoRepository;
import veinthrough.tacocloud.model.Taco;
import veinthrough.tacocloud.property.PageSizeProps;
import veinthrough.tacocloud.rest.resource.RecentTacosController;

import static org.mockito.Mockito.when;
import static veinthrough.tacocloud.rest.TacoSupplier.getTaco;

public class RecentTacosControllerTest {
    private static final String RECENT_URL = "tacos/recent";

    @Test
    public void shouldReturnRecentTacos() {
        Taco[] tacos = {
                getTaco(1L), getTaco(2L),
                getTaco(3L), getTaco(4L),
                getTaco(5L), getTaco(6L),
                getTaco(7L), getTaco(8L),
                getTaco(9L), getTaco(10L),
                getTaco(11L), getTaco(12L),
                getTaco(13L), getTaco(14L),
                getTaco(15L), getTaco(16L)};
        Flux<Taco> tacoFlux = Flux.just(tacos);

        // mock tacoRepository
        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        when(tacoRepo.findAll()).thenReturn(tacoFlux);

        // pageSizeProps
        PageSizeProps pageSizeProps = new PageSizeProps();
        pageSizeProps.setPageSizes(
                ImmutableMap.<String, Integer>builder()
                        .put("recent", 10)
                        .build());

        // bind to controller
        WebTestClient testClient = WebTestClient.bindToController(
                new RecentTacosController(tacoRepo, pageSizeProps))
                .build();

        testClient.get().uri(RECENT_URL)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo("Taco 1")
                .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
                .jsonPath("$[1].name").isEqualTo("Taco 2")
                .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
                .jsonPath("$[11].name").isEqualTo("Taco 12")
                .jsonPath("$[12]").doesNotExist();
    }
}
