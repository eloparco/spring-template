package com.example.demo.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Value("${spring.jwt.secret}")
  private String secret;
  @Value("${spring.jwt.expiration}")
  private String expireTimeInMiliSec;
  SecretKey secretKey;

  @PostConstruct
  protected void init() {
    String base64EncodedSecretKey = Base64.getEncoder().encodeToString(secret.getBytes());
    secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedSecretKey));
  }

  public String generateToken(User user) {
    Date creationDate = new Date();
    Date expirationDate =
        new Date(creationDate.getTime() + Long.parseLong(expireTimeInMiliSec) * 1000);

    Map<String, Object> claim = new HashMap<>();
    claim.put("alg", "HS256");
    claim.put("typ", "JWT");


    return Jwts.builder().setClaims(claim).setSubject(user.getUsername()).setIssuedAt(creationDate)
        .setExpiration(expirationDate).signWith(secretKey, SignatureAlgorithm.HS256).compact();
  }

  public Claims getClaimsFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
  }

  public String getUsernameFromToken(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  public Boolean isTokenValidated(String token) {
    Date expirationDate = getClaimsFromToken(token).getExpiration();
    return !expirationDate.before(new Date());
  }
}

