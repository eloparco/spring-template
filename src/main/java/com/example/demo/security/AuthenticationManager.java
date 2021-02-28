package com.example.demo.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

  private JwtUtil jwtUtil;
  private UserRepository userRepository;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();
    String userName = jwtUtil.getUsernameFromToken(token);

    return userRepository.findByUsername(userName).flatMap(userDetails -> {
      if (userName.equals(userDetails.getUsername()) && jwtUtil.isTokenValidated(token)) {
        return Mono.just(authentication);
      } else {
        return Mono.empty();
      }
    });
  }
}

