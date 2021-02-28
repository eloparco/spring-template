package com.example.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.example.demo.model.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
