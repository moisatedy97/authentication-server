package org.tedygabrielmoisa.authenticationserver.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
  /**
   * The unique identifier for the token.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;

  /**
   * The token string which is unique across all tokens.
   */
  @Column(unique = true)
  public String token;

  /**
   * Flag indicating whether the token has been revoked.
   */
  public boolean revoked;

  /**
   * Flag indicating whether the token has expired.
   */
  public boolean expired;

  /**
   * The user to whom the token is associated.
   * This is a many-to-one relationship as a user can have multiple tokens.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User user;
}
