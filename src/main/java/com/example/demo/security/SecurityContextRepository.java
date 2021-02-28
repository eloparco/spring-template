package com.example.demo.security;

import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

  private AuthenticationManager authenticationManager;
  public static final String HEADER_PREFIX = "Bearer ";

  @Override
  public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
    return Mono.empty();
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
    return Mono
        .justOrEmpty(
            serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
        .filter(b -> b.startsWith(HEADER_PREFIX))
        .map(subs -> subs.substring(HEADER_PREFIX.length()))
        .flatMap(token -> Mono.just(new UsernamePasswordAuthenticationToken(token, token,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))))
        .flatMap(auth -> authenticationManager.authenticate(auth).map(SecurityContextImpl::new));
  }
}

