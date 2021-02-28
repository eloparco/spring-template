package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

  private ProductService productService;

  @GetMapping
  public Flux<Product> findAll() {
    return productService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Product>> findById(@PathVariable(value = "id") String productId) {
    return productService.findById(productId)
        .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Mono<ResponseEntity<Product>> save(@RequestBody Product product) {
    return productService.save(product)
        .map(savedProduct -> new ResponseEntity<>(savedProduct, HttpStatus.CREATED))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Product>> update(@PathVariable(value = "id") String productId,
      @RequestBody Product product) {
    return productService.update(productId, product)
        .map(updatedProduct -> new ResponseEntity<>(updatedProduct, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Product>> deleteById(@PathVariable(value = "id") String productId) {
    return productService.findById(productId)
        .flatMap(product -> productService.delete(productId)
            .thenReturn(new ResponseEntity<>(product, HttpStatus.OK)))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
