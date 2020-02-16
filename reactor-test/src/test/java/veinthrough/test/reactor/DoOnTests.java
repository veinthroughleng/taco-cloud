package veinthrough.test.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class DoOnTests {
    @Test
    public void doOnNextTest() {
        Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry")
                .doOnNext(item -> log.info("{}",item))
                .buffer()
                .next()
                .doOnNext(item -> log.info("{}",item))
                .subscribe();
    }

    @Test
    public void doOnNextEach() {
        Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry")
                .doOnEach(item -> log.info("{}",item))
                .buffer()
                .next()
                .doOnEach(item -> log.info("{}",item))
                .subscribe();
    }
}
