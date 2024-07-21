package org.tedygabrielmoisa.authenticationserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tedygabrielmoisa.authenticationserver.entities.Otp;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link Otp} entities.
 */
public interface OtpRepository extends JpaRepository<Otp, Integer> {

  /**
   * Finds an {@link Otp} entity by the user's ID.
   *
   * @param userId the ID of the user.
   * @return an {@link Optional} containing the found {@link Otp} or empty if none found.
   */
  Optional<Otp> findOtpByUserId(Integer userId);

  /**
   * Finds an {@link Otp} entity by the user's email.
   *
   * @param email the email of the user.
   * @return an {@link Optional} containing the found {@link Otp} or empty if none found.
   */
  Optional<Otp> findOtpByUserEmail(String email);

}
