package com.project.microservices.app.service.product.models.service;

import com.project.microservices.library.commons.constants.Status;
import com.project.microservices.library.commons.models.entity.product.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Product save(Product product);

    void deleteById(Long id);

    List<Product> findAllByStatus(Status status);
}
