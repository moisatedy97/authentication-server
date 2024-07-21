package org.tedygabrielmoisa.authenticationserver.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing a One-Time Password (OTP).
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otps")
public class Otp {

    /**
     * The unique identifier for the OTP.
     */
    @Id
    private Integer id;

    /**
     * The user associated with the OTP.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The One-Time Password.
     */
    private String otp;

    /**
     * The time when the OTP was created.
     */
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The time when the OTP expires.
     */
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime expiresAt = LocalDateTime.now();
}
