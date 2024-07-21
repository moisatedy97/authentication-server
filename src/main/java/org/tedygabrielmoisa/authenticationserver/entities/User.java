package org.tedygabrielmoisa.authenticationserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.tedygabrielmoisa.authenticationserver.enums.Gender;
import org.tedygabrielmoisa.authenticationserver.enums.Role;
import org.tedygabrielmoisa.authenticationserver.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a user entity in the authentication server.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

  /**
   * Unique identifier for the user.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * Email address of the user. It is unique and cannot be null.
   */
  @Column(nullable = false, unique = true, length = 100)
  private String email;

  /**
   * Password hash of the user. It is ignored in JSON responses and cannot be null.
   */
  @JsonIgnore
  @Column(nullable = false, length = 60)
  private String passwordHash;

  /**
   * First name of the user.
   */
  @Column(length = 50)
  private String firstName;

  /**
   * Last name of the user.
   */
  @Column(length = 50)
  private String lastName;

  /**
   * Phone number of the user.
   */
  @Column(length = 15)
  private String phoneNumber;

  /**
   * Date of birth of the user.
   */
  private LocalDate dateOfBirth;

  /**
   * Gender of the user.
   */
  @Enumerated(EnumType.STRING)
  @Column(length = 10)
  private Gender gender;

  /**
   * URL of the user's profile picture.
   */
  @Column(length = 255)
  private String profilePictureUrl;

  /**
   * Short biography of the user.
   */
  @Lob
  private String bio;

  /**
   * Website URL of the user.
   */
  @Column(length = 255)
  private String websiteUrl;

  /**
   * The date and time when the user was created.
   * Defaults to the current time.
   */
  @Builder.Default
  @Column(nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  /**
   * The date and time when the user was last updated.
   * Defaults to the current time.
   */
  @Builder.Default
  @Column(nullable = false)
  private LocalDateTime updatedAt = LocalDateTime.now();

  /**
   * The date and time when the user last logged in.
   */
  private LocalDateTime lastLogin;

  /**
   * Status of the user. It cannot be null and defaults to ACTIVE.
   */
  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Status status = Status.ACTIVE;

  /**
   * Role of the user. It cannot be null and defaults to USER.
   */
  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Role role = Role.USER;

  /**
   * Address of the user.
   */
  @Column(length = 255)
  private String address;

  /**
   * City of the user.
   */
  @Column(length = 100)
  private String city;

  /**
   * State of the user.
   */
  @Column(length = 100)
  private String state;

  /**
   * Postal code of the user.
   */
  @Column(length = 20)
  private String postalCode;

  /**
   * Country of the user.
   */
  @Column(length = 100)
  private String country;

  /**
   * URL of the user's Facebook profile.
   */
  @Column(length = 255)
  private String facebookProfileUrl;

  /**
   * URL of the user's Twitter profile.
   */
  @Column(length = 255)
  private String twitterProfileUrl;

  /**
   * URL of the user's LinkedIn profile.
   */
  @Column(length = 255)
  private String linkedinProfileUrl;

  /**
   * URL of the user's Instagram profile.
   */
  @Column(length = 255)
  private String instagramProfileUrl;
}
