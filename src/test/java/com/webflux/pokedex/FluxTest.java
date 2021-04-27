package com.webflux.pokedex;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxTest {

    @Test
    void testeFlux1(){
        Flux.empty();
    }

    @Test
    void testeFlux2(){
        Flux<String> flux = Flux.just("Pokedex");
        flux.subscribe(System.out::println);
    }

    @Test
    void testeFluxoPokemons(){
        Flux<String> flux = Flux.just("Sharizard", "Blatoise", "Picachu");
        flux.subscribe(System.out::println);
    }
}
