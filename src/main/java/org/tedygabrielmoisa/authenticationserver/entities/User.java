package org.tedygabrielmoisa.authenticationserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.tedygabrielmoisa.authenticationserver.enums.Gender;
import org.tedygabrielmoisa.authenticationserver.enums.Role;
import org.tedygabrielmoisa.authenticationserver.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @JsonIgnore
  @Column(nullable = false, length = 60)
  private String passwordHash;

  @Column(length = 50)
  private String firstName;

  @Column(length = 50)
  private String lastName;

  @Column(length = 15)
  private String phoneNumber;

  private LocalDate dateOfBirth;

  @Enumerated(EnumType.STRING)
  @Column(length = 10)
  private Gender gender;

  @Column(length = 255)
  private String profilePictureUrl;

  @Lob
  private String bio;

  @Column(length = 255)
  private String websiteUrl;

  @Builder.Default
  @Column(nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder.Default
  @Column(nullable = false)
  private LocalDateTime updatedAt = LocalDateTime.now();

  private LocalDateTime lastLogin;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Status status = Status.ACTIVE;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Role role = Role.USER;

  @Column(length = 255)
  private String address;

  @Column(length = 100)
  private String city;

  @Column(length = 100)
  private String state;

  @Column(length = 20)
  private String postalCode;

  @Column(length = 100)
  private String country;

  @Column(length = 255)
  private String facebookProfileUrl;

  @Column(length = 255)
  private String twitterProfileUrl;

  @Column(length = 255)
  private String linkedinProfileUrl;

  @Column(length = 255)
  private String instagramProfileUrl;
}
