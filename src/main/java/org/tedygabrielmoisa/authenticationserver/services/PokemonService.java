package org.tedygabrielmoisa.authenticationserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tedygabrielmoisa.authenticationserver.entities.Pokemon;
import org.tedygabrielmoisa.authenticationserver.repositories.PokemonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    public List<Pokemon> getPokemons() {
        return pokemonRepository.findAll();
    }

    public Pokemon createPokemon(Pokemon pokemonDto) {
        pokemonRepository.save(pokemonDto);

        return pokemonDto;
    }

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

    public void deletePokemon(Integer id) {
        pokemonRepository.deleteById(id);
    }
}
