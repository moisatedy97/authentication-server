package org.tedygabrielmoisa.authenticationserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tedygabrielmoisa.authenticationserver.entities.Otp;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 * Service responsible for generating and validating OTPs (One-Time Passwords).
 */
@Service
@RequiredArgsConstructor
public class OtpService {

    /**
     * Generates a new OTP (One-Time Password) using a strong instance of {@link SecureRandom}.
     *
     * @return the generated OTP as a string
     * @throws RuntimeException if an error occurs while generating the OTP
     */
    public String generateOtp() throws RuntimeException {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            return String.valueOf(random.nextInt(9000) + 1000);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the OTP", e);
        }
    }

    /**
     * Checks if the provided OTP is still valid by comparing its expiration time to the current time.
     *
     * @param otp the OTP to check for validity
     * @return true if the OTP is still valid, false otherwise
     */
    public boolean checkOtpIsValid(Otp otp) {
        return otp.getExpiresAt().isAfter(LocalDateTime.now());
    }
}
