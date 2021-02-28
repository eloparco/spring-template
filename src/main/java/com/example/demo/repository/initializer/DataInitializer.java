package com.example.demo.repository.initializer;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Component
@Profile("dev")
@Slf4j
public class DataInitializer implements CommandLineRunner {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ProductRepository productRepository;

  @Override
  public void run(String... args) throws Exception {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    userRepository.deleteAll()
        .thenMany(
            Flux.just("eloparco").map(user -> new User(user, passwordEncoder.encode("password")))
                .flatMap(userRepository::save))
        .thenMany(userRepository.findAll()).subscribe(user -> log.info("User {}", user));

    productRepository.deleteAll()
        .thenMany(Flux.just("Macbook Pro", "Macbook Air", "Ipad Pro", "Ipad Air", "Ipad Mini")
            .map(
                name -> new Product(null, name, ThreadLocalRandom.current().nextDouble(1000, 5000)))
            .flatMap(productRepository::save))
        .thenMany(productRepository.findAll())
        .subscribe(product -> log.info("Saved product {}", product));
  }

}
