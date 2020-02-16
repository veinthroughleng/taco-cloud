package veinthrough.tacocloud.rest;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import veinthrough.tacocloud.data.TacoRepository;
import veinthrough.tacocloud.model.Taco;
import veinthrough.tacocloud.rest.basic.DesignTacoController;

import java.util.Objects;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static veinthrough.tacocloud.rest.TacoSupplier.getTaco;

public class DesignTacoControllerTest {
    @Test
    public void shouldSaveATaco() {
        // mock tacoRepository
        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        Mono<Taco> unsavedTacoMono = Mono.just(getTaco(1L));
        Mono<Taco> savedTacoMono = Mono.fromSupplier(() -> {
            Taco savedTaco = TacoSupplier.getTaco(1L);
            savedTaco.setId(UUID.randomUUID());
            return savedTaco;
        });
        when(tacoRepo.save(any())).thenReturn(savedTacoMono);

        // bind to controller
        WebTestClient testClient = WebTestClient.bindToController(
                new DesignTacoController(tacoRepo)).build();

        testClient.post()
                .uri("/design")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedTacoMono, Taco.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Taco.class)
                .isEqualTo(Objects.requireNonNull(savedTacoMono.block()));
    }
}
