package org.tedygabrielmoisa.authenticationserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tedygabrielmoisa.authenticationserver.entities.Pokemon;
import org.tedygabrielmoisa.authenticationserver.repositories.PokemonRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for handling CRUD operations for Pokemon entities.
 */
@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    /**
     * Retrieves a list of all Pokemon entities from the repository.
     *
     * @return a list of all Pokemon
     */
    public List<Pokemon> getPokemons() {
        return pokemonRepository.findAll();
    }

    /**
     * Creates a new Pokemon entity and saves it to the repository.
     *
     * @param pokemonDto the Pokemon entity to be created
     * @return the created Pokemon entity
     */
    public Pokemon createPokemon(Pokemon pokemonDto) {
        pokemonRepository.save(pokemonDto);
        return pokemonDto;
    }

    /**
     * Updates an existing Pokemon entity with new data.
     *
     * @param id the ID of the Pokemon entity to be updated
     * @param pokemonDto the new data for the Pokemon entity
     * @return the updated Pokemon entity, or null if the Pokemon entity could not be found
     */
    public Pokemon updatePokemon(Integer id, Pokemon pokemonDto) {
        Optional<Pokemon> pokemon = pokemonRepository.findById(id);
        if (pokemon.isEmpty()) {
            return null;
        }

        Pokemon currentPokemon = pokemon.get();
        currentPokemon.setName(pokemonDto.getName());
        currentPokemon.setType1(pokemonDto.getType1());
        currentPokemon.setType2(pokemonDto.getType2());
        currentPokemon.setAbilities(pokemonDto.getAbilities());
        currentPokemon.setHp(pokemonDto.getHp());
        currentPokemon.setAttack(pokemonDto.getAttack());
        currentPokemon.setDefense(pokemonDto.getDefense());
        currentPokemon.setSpecialAttack(pokemonDto.getSpecialAttack());
        currentPokemon.setSpecialDefense(pokemonDto.getSpecialDefense());
        currentPokemon.setSpeed(pokemonDto.getSpeed());

        pokemonRepository.save(currentPokemon);
        return currentPokemon;
    }

    /**
     * Deletes a Pokemon entity from the repository by its ID.
     *
     * @param id the ID of the Pokemon entity to be deleted
     */
    public void deletePokemon(Integer id) {
        pokemonRepository.deleteById(id);
    }
}
