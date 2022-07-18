package com.project.microservices.app.service.product.models.service;

import com.project.microservices.library.commons.constants.Status;
import com.project.microservices.library.commons.models.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    Page<Product> findAll(Pageable pageable);

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    boolean existsById(Long id);

    Product save(Product product);

    void deleteById(Long id);

    List<Product> findAllByStatus(Status status);
}
