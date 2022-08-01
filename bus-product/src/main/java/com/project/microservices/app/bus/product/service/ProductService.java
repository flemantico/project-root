package com.project.microservices.app.bus.product.service;

import com.project.microservices.library.commons.constants.Status;
import com.project.microservices.library.commons.models.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Page<Product>> page(Pageable pageable);

    Optional<List<Product>> all();

    Optional<Product> find(Long id);

    Optional<Product> findByName(String name);

    Optional<Boolean> exists(Long id);

    Optional<Product> save(Product product);

    void delete(Long id);

    Optional<List<Product>> findAllByStatus(Status status);
}
