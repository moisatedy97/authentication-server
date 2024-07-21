package org.tedygabrielmoisa.authenticationserver.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.tedygabrielmoisa.authenticationserver.entities.Otp;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
    Optional<Otp> findOtpByUserId(Integer userId);

    Optional<Otp> findOtpByUserEmail(String email);

}
