package org.tedygabrielmoisa.authenticationserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tedygabrielmoisa.authenticationserver.entities.User;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Finds a {@link User} entity by its email.
   *
   * @param email the email of the user to be found
   * @return an {@link Optional} containing the found {@link User}, or an empty {@link Optional} if no user found
   */
  Optional<User> findUserByEmail(String email);
}
