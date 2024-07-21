package org.tedygabrielmoisa.authenticationserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tedygabrielmoisa.authenticationserver.entities.Otp;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {
    public String generateOtp() throws RuntimeException {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();

            return String.valueOf(random.nextInt(9000) + 1000);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the OTP", e);
        }
    }

    public boolean checkOtpIsValid(Otp otp) {
        return otp.getExpiresAt().isAfter(LocalDateTime.now());
    }
}
