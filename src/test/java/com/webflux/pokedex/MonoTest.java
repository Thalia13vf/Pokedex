package com.webflux.pokedex;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;


public class MonoTest {
    @Test
    public void testeMono1(){
        Mono<String>  mono = Mono.empty();
    }

    @Test
    public void testeMono2(){
        Mono<String> mono = Mono.just("Pokemon");
        mono.subscribe(System.out::println);
    }

    @Test
    public void testeMono3(){
        Mono<Integer> mono = Mono.just(10);
        mono.subscribe(System.out::println);
    }
    @Test
    public void testeMono4(){
        Mono<String> mono = Mono.error(new RuntimeException("Ocorreu uma exceção!"));

    }

}
