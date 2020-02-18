package veinthrough.test.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ListToMapTests {
    @Test
    //duplicate key: override
    public void listToMapTest() {
        Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo")
                .doOnNext(item -> log.info("{}", item))
                .collectMap(a -> a.charAt(0))
                .doOnNext(item -> log.info("{}", item))
                .subscribe();
    }

    @Test
    //duplicate key: to list
    public void listToMapTest2() {
        Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo")
                .doOnNext(item -> log.info("{}", item))
                //
                .collectMultimap(a -> a.charAt(0))
                .doOnNext(item -> log.info("{}", item))
                .subscribe();
    }

    @Test
    //duplicate key: to list
    public void listToMapTest3() {
        Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo")
                .doOnNext(item -> log.info("{}", item))
                .collect(
                        Collectors.groupingBy(str -> str.charAt(0)))
                .doOnNext(item -> log.info("{}", item))
                .subscribe();
    }

    @Test
    //duplicate key: error
    public void listToMapTest4() {
        Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo")
                .doOnNext(item -> log.info("{}", item))
                //java.lang.IllegalStateException: Duplicate key elephant
                .collect(
                        Collectors.toMap(str -> str.charAt(0),
                                item -> item))
                .doOnNext(item -> log.info("{}", item))
                .subscribe();
    }

    @Test
    //duplicate key: error
    public void listToMapTest5() {
        Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo")
                .doOnNext(item -> log.info("{}", item))
                //java.lang.IllegalStateException: Duplicate key elephant
                .collect(
                        Collectors.toMap(str -> str.charAt(0),
                                Function.identity()))
                .doOnNext(item -> log.info("{}", item))
                .subscribe();
    }

    @Test
    //duplicate key: override
    public void listToMapTest6() {
        Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo")
                .doOnNext(item -> log.info("{}", item))
                // just item2 override item1
                .collect(
                        Collectors.toMap(str -> str.charAt(0),
                                Function.identity(),
                                (item1, item2) -> item2))
                .doOnNext(item -> log.info("{}", item))
                .subscribe();
    }
}
