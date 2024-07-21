package org.tedygabrielmoisa.authenticationserver.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tedygabrielmoisa.authenticationserver.entities.Pokemon;
import org.tedygabrielmoisa.authenticationserver.services.PokemonService;

import java.util.List;

/**
 * REST controller for handling Pokemon-related requests.
 */
@RestController
@RequiredArgsConstructor
public class PokemonController {
    private final PokemonService pokemonService;

    /**
     * Retrieves the list of all Pokemon.
     *
     * @return a {@link ResponseEntity} containing the list of all Pokemon
     */
    @GetMapping(value = "/pokemons")
    public ResponseEntity<List<Pokemon>> getPokemons() {
        List<Pokemon> pokemons = pokemonService.getPokemons();
        return ResponseEntity.ok(pokemons);
    }

    /**
     * Creates a new Pokemon.
     *
     * @param pokemonDto the Pokemon details
     * @return a {@link ResponseEntity} containing the created Pokemon
     */
    @PostMapping(value = "/pokemons/create")
    public ResponseEntity<Pokemon> createPokemon(@Valid @RequestBody Pokemon pokemonDto) {
        Pokemon pokemon = pokemonService.createPokemon(pokemonDto);
        return ResponseEntity.ok(pokemon);
    }

    /**
     * Updates an existing Pokemon.
     *
     * @param id         the ID of the Pokemon to update
     * @param pokemonDto the updated Pokemon details
     * @return a {@link ResponseEntity} containing the updated Pokemon, or a {@link ResponseEntity#notFound()} if the Pokemon does not exist
     */
    @PutMapping(value = "/pokemons/update/{id}")
    public ResponseEntity<Pokemon> updatePokemon(@PathVariable Integer id, @Valid @RequestBody Pokemon pokemonDto) {
        Pokemon pokemon = pokemonService.updatePokemon(id, pokemonDto);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pokemon);
    }

    /**
     * Deletes a Pokemon by ID.
     *
     * @param id the ID of the Pokemon to delete
     * @return a {@link ResponseEntity#noContent()} if the deletion is successful
     */
    @DeleteMapping(value = "/pokemons/delete/{id}")
    public ResponseEntity<Void> deletePokemon(@PathVariable Integer id) {
        pokemonService.deletePokemon(id);
        return ResponseEntity.noContent().build();
    }
}
