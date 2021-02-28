package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  @Override
  public Mono<Product> findById(String productId) {
    return productRepository.findById(productId);
  }

  @Override
  public Flux<Product> findAll() {
    return productRepository.findAll();
  }

  @Override
  public Mono<Product> save(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Mono<Product> delete(String productId) {
    return productRepository.findById(productId)
        .flatMap(product -> productRepository.deleteById(product.getId()).thenReturn(product));
  }

  @Override
  public Mono<Product> update(String productId, Product product) {
    return productRepository.findById(productId).flatMap(savedProduct -> {
      savedProduct.setName(product.getName());
      savedProduct.setPrice(product.getPrice());
      return productRepository.save(savedProduct);
    });
  }

}
