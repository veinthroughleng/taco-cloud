package veinthrough.test.reactor;

import org.junit.Ignore;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class FluxLoggingTests {

    @Test
    @Ignore
    //onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
    public void logSimple() {
        Flux<String> beltColors = Flux.just(
                "white", "yellow", "orange", "green", "purple", "blue")
                .log();
        beltColors.subscribe();
    }

    @Test
    @Ignore
    public void logMapping() {
        Flux<String> beltColors = Flux.just(
                "white", "yellow", "orange", "green", "purple", "blue")
                .map(String::toUpperCase)
                .log();

        beltColors.subscribe();
    }

    @Test
    //onSubscribe([Fuseable] FluxMapFuseable.MapFuseableSubscriber)
    public void logFlatMapping() throws InterruptedException {
        Flux<String> beltColors = Flux.just(
                "white", "yellow", "orange", "green", "purple", "blue")
                .flatMap(color ->
                        Mono.just(color)
                                .map(String::toUpperCase)
                                .log()
                                .subscribeOn(Schedulers.parallel()));

        beltColors.subscribe();
        Thread.sleep(3000L);
    }
}
