package com.webflux.pokedex.controller;

import com.webflux.pokedex.model.Pokemon;
import com.webflux.pokedex.model.PokemonEvent;
import com.webflux.pokedex.repository.PokedexRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

@RestController
@RequestMapping("api/v1/pokemons")
public class PokemonController {
    private PokedexRepository pokedexRepository;

    public PokemonController(PokedexRepository pokemonRepository) {
        this.pokedexRepository = pokemonRepository;
    }
    
    @GetMapping
    public Flux<Pokemon> getAllPokemons(){
        return pokedexRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Pokemon>> getPokemonById(@PathVariable String id){
        return pokedexRepository.findById(id)
                .map(pokemon -> ResponseEntity.ok(pokemon)) //sucesso
                .defaultIfEmpty(ResponseEntity.notFound().build()); //não achou. ta vazio. Erro
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pokemon> savePokemon(@RequestBody Pokemon pokemon){
        return pokedexRepository.save(pokemon);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Pokemon>> updatePokemon(@PathVariable String id, @RequestBody Pokemon pokemon){

        return pokedexRepository.findById(id)
                .flatMap(existingPokemon -> {
                    existingPokemon.setNome(pokemon.getNome());
                    existingPokemon.setHabilidade(pokemon.getHabilidade());
                    existingPokemon.setCategoria(pokemon.getCategoria());
                    existingPokemon.setPeso(pokemon.getPeso());
                    return pokedexRepository.save(existingPokemon);
        })
                .map(updatePokemon -> ResponseEntity.ok(updatePokemon))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deletePokemon (@PathVariable String id){
        return pokedexRepository.findById(id)
                .flatMap(existingPokemon ->
                     pokedexRepository.delete(existingPokemon)
                     .then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllPokemon(){
        return pokedexRepository.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PokemonEvent> getPokemonEvent(){
        return Flux.interval(Duration.ofSeconds(5))
                .map(val -> new PokemonEvent(val, "Evento de pokemon"));
    }

}
