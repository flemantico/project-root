package com.project.microservices.app.bus.product.service;

import com.project.microservices.library.commons.constants.Status;
import com.project.microservices.library.commons.model.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Page<Product>> page(Pageable pageable);

    Optional<List<Product>> all();

    Optional<Product> find(Long id);

    Optional<Boolean> exists(Long id);

    Optional<Product> save(Product product);

    Optional<Product> save(Product product, Long id);

    void delete(Long id);

    Optional<Product> findByName(String name);

    Optional<List<Product>> findAllByStatus(Status status);
}
