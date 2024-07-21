package org.tedygabrielmoisa.authenticationserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tedygabrielmoisa.authenticationserver.entities.Token;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the repository handling Token entities.
 * This repository provides an abstraction layer to perform various operations on Token data in the database.
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {

  /**
   * Retrieves a list of all valid tokens associated with a specific user.
   * A token is considered valid if it is neither expired nor revoked.
   *
   * @param userId the unique identifier of the user whose tokens are to be retrieved
   * @return a list of valid Token entities
   */
  @Query(value = """
            select token from Token token inner join User user\s
            on token.user.id = user.id\s
            where user.id = :userId and (token.expired = false or token.revoked = false)\s
            """)
  List<Token> findAllValidTokenByUser(Integer userId);

  /**
   * Finds a specific token by its token string.
   *
   * @param token the token string to search for
   * @return an Optional containing the Token if found, otherwise an empty Optional
   */
  Optional<Token> findByToken(String token);

  /**
   * Finds tokens associated with a specific user by their user ID.
   *
   * @param userId the unique identifier of the user
   * @return an Optional containing a list of Token entities if found, otherwise an empty Optional
   */
  Optional<List<Token>> findByUserId(Integer userId);
}
