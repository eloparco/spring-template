package com.example.demo.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.SignupRequest;
import com.example.demo.dto.response.ErrorResponse;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

  private UserRepository userRepository;
  private JwtUtil jwtUtil;
  PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public Mono<ResponseEntity<?>> getToken(@Valid @RequestBody LoginRequest loginRequest) {
    User user = new User(loginRequest.getUsername(), loginRequest.getPassword());

    return userRepository.findByUsername(loginRequest.getUsername()).map((userDetails) -> {
      if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
        return new ResponseEntity<>(new JwtResponse(jwtUtil.generateToken(user)), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(new ErrorResponse("Invalid credentials"),
            HttpStatus.BAD_REQUEST);
      }
    }).defaultIfEmpty(
        new ResponseEntity<>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST));
  }

  @PostMapping("/signup")
  public Mono<ResponseEntity<SignupRequest>> signup(
      @Valid @RequestBody SignupRequest signupRequest) {
    String encodedPsw = passwordEncoder.encode(signupRequest.getPassword());
    log.info("Encoded Password: {}", encodedPsw);
    User user = new User(signupRequest.getUsername(), encodedPsw);

    // DuplicateKeyException is handled in ControllerExceptionHandler class
    return userRepository.save(user).map(userDetails -> {
      return new ResponseEntity<>(signupRequest, HttpStatus.CREATED);
    });
  }
}

