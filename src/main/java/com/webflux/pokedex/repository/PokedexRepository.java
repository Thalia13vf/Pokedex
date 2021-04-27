package com.webflux.pokedex.repository;

import com.webflux.pokedex.model.Pokemon;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PokedexRepository extends ReactiveMongoRepository<Pokemon, String> {
}
