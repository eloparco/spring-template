package com.example.demo.service;

import com.example.demo.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Mono<Product> findById(String productId);

  Flux<Product> findAll();

  Mono<Product> save(Product product);

  Mono<Product> delete(String productId);

  Mono<Product> update(String productId, Product product);
}
