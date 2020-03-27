package veinthrough.test.reactive;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
public class MonoFluxInterconversionTests {
    @Test
    public void fluxToMonoTest() {
        Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry")
                .doOnNext(item -> log.info("{}", item))
                .buffer()
                .next()
                .doOnNext(item -> log.info("{}", item))
                .subscribe();
    }
    @Test
    public void fluxToMonoTest2() {
        Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry")
                .doOnNext(item -> log.info("{}", item))
                .collectList()
                .doOnNext(item -> log.info("{}", item))
                .subscribe();
    }

    @Test
    public void monoToFluxTest() {
        Mono.just(Lists.newArrayList("basketball", "football", "badminton"))
                .doOnNext(item -> log.info("{}", item))
                .flatMapMany(Flux::fromIterable)
                .doOnNext(item -> log.info("{}", item))
                .subscribe();
    }

    @Test
    public void monoMapToFluxTest() {
        Map<String, List<String>> inhabits = ImmutableMap.<String, List<String>>builder()
                .put("Tom", Lists.newArrayList("basketball", "football", "badminton"))
                .put("Jim", Lists.newArrayList("basketball", "football"))
                .put("Sam", Lists.newArrayList("basketball"))
                .build();

        Flux.fromIterable(inhabits.keySet())
                .doOnNext(
                        name -> Mono.just(name)
                                .flatMapMany(key -> Flux.fromIterable(
                                        inhabits.get(key)
                                ))
                                .doOnNext(item -> log.info("{}: {}", name, item))
                                .subscribe())
                .subscribe();

    }
}
