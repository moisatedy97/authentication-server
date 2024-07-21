package org.tedygabrielmoisa.authenticationserver.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.tedygabrielmoisa.authenticationserver.dto.LoginResDto;
import org.tedygabrielmoisa.authenticationserver.dto.LoginUserDto;
import org.tedygabrielmoisa.authenticationserver.dto.RegisterUserDto;
import org.tedygabrielmoisa.authenticationserver.entities.User;
import org.tedygabrielmoisa.authenticationserver.repositories.UserRepository;
import org.tedygabrielmoisa.authenticationserver.services.AuthenticationService;
import org.tedygabrielmoisa.authenticationserver.services.JwtService;

import java.util.Optional;

/**
 * REST controller for handling authentication and user-related requests.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

  private final JwtService jwtService;
  private final AuthenticationService authenticationService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * Handles login requests.
   *
   * @param loginUserDto the user login details
   * @param response the HTTP servlet response
   * @return a {@link ResponseEntity} with the login response details
   */
  @GetMapping(value = "/login")
  public ResponseEntity<LoginResDto> login(@Valid LoginUserDto loginUserDto, HttpServletResponse response) {
    Authentication authentication = authenticationService.authenticate(loginUserDto);

    if (authentication == null) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    Optional<User> dbUser = userRepository.findUserByEmail(loginUserDto.getEmail());

    if (dbUser.isPresent()) {
      User currentUser = dbUser.get();

      if (authentication.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return processAuthenticatedUser(currentUser, response);
      } else {
        authenticationService.saveUserOtp(currentUser);

        return ResponseEntity.status(HttpServletResponse.SC_PARTIAL_CONTENT).build();
      }
    }

    return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
  }

  /**
   * Handles user registration requests.
   *
   * @param registerUserDto the user registration details
   * @return a {@link ResponseEntity} indicating the result of the registration
   */
  @PostMapping(value = "/register")
  public ResponseEntity<LoginResDto> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
    User user = User.builder()
            .firstName(registerUserDto.getFirstName())
            .lastName(registerUserDto.getLastName())
            .email(registerUserDto.getEmail())
            .passwordHash(passwordEncoder.encode(registerUserDto.getPassword()))
            .build();

    try {
      userRepository.save(user);
    } catch (Exception e) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    return ResponseEntity.status(HttpServletResponse.SC_CREATED).build();
  }

  /**
   * Checks if the user is authenticated.
   *
   * @param response the HTTP servlet response
   * @return a {@link ResponseEntity} with the authentication check response
   */
  @GetMapping(value = "/checkAuthenticated")
  public ResponseEntity<LoginResDto> checkAuthenticated(HttpServletResponse response) {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    String principal = SecurityContextHolder.getContext().getAuthentication().getName();
    Optional<User> dbUser = userRepository.findUserByEmail(principal);

    if (dbUser.isEmpty()) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    User currentUser = dbUser.get();

    return processAuthenticatedUser(currentUser, response);
  }

  /**
   * Handles user logout requests.
   *
   * @param request the HTTP servlet request
   * @return a {@link ResponseEntity} indicating the result of the logout
   */
  @GetMapping(value = "/logout")
  public ResponseEntity<HttpServletResponse> logout(HttpServletRequest request) {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    SecurityContextLogoutHandler contextLogoutHandler = new SecurityContextLogoutHandler();
    String principal = SecurityContextHolder.getContext().getAuthentication().getName();
    Optional<User> dbUser = userRepository.findUserByEmail(principal);

    if (dbUser.isEmpty()) {
      return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    User currentUser = dbUser.get();

    authenticationService.revokeAllUserTokens(currentUser);
    contextLogoutHandler.logout(request, null, null);

    return ResponseEntity.status(HttpServletResponse.SC_ACCEPTED).build();
  }

  /**
   * Processes the authenticated user and generates the response.
   *
   * @param currentUser the authenticated user
   * @param response the HTTP servlet response
   * @return a {@link ResponseEntity} with the login response details
   */
  private ResponseEntity<LoginResDto> processAuthenticatedUser(User currentUser, HttpServletResponse response) {
    String jwtToken = jwtService.generateJwtToken(currentUser);
    String jwtRefreshToken = jwtService.generateJwtRefreshToken(currentUser);

    authenticationService.revokeAllUserTokens(currentUser);
    authenticationService.saveUserToken(currentUser, jwtRefreshToken);
    authenticationService.setCookieToken(jwtRefreshToken, response);

    return ResponseEntity.ok(LoginResDto.builder()
            .user(currentUser)
            .token(jwtToken)
            .build()
    );
  }
}
