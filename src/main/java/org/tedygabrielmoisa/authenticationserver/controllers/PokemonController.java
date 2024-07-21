package org.tedygabrielmoisa.authenticationserver.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tedygabrielmoisa.authenticationserver.entities.Pokemon;
import org.tedygabrielmoisa.authenticationserver.services.PokemonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PokemonController {
    private final PokemonService pokemonService;

    @GetMapping(value = "/pokemons")
    public ResponseEntity<List<Pokemon>> getPokemons() {
        List<Pokemon> pokemons = pokemonService.getPokemons();

        return ResponseEntity.ok(pokemons);
    }

    @PostMapping(value = "/pokemons/create")
    public ResponseEntity<Pokemon> createPokemon(@Valid @RequestBody Pokemon pokemonDto) {
        Pokemon pokemon = pokemonService.createPokemon(pokemonDto);

        return ResponseEntity.ok(pokemon);
    }

    @PutMapping(value = "/pokemons/update/{id}")
    public ResponseEntity<Pokemon> updatePokemon(@PathVariable Integer id, @Valid @RequestBody Pokemon pokemonDto) {
        Pokemon pokemon = pokemonService.updatePokemon(id, pokemonDto);

        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pokemon);
    }

    @DeleteMapping(value = "/pokemons/delete/{id}")
    public ResponseEntity<Void> deletePokemon(@PathVariable Integer id) {
        pokemonService.deletePokemon(id);

        return ResponseEntity.noContent().build();
    }
}
