package org.tedygabrielmoisa.authenticationserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tedygabrielmoisa.authenticationserver.entities.Pokemon;

/**
 * Repository interface for performing CRUD operations on {@link Pokemon} entities.
 */
@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
}
