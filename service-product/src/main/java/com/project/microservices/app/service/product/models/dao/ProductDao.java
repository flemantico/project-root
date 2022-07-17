package com.project.microservices.app.service.product.models.dao;

import com.project.microservices.library.commons.constants.Status;
import com.project.microservices.library.commons.models.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long> {

    List<Product> findAllByStatus(Status status);

    Optional<Product> findByName(String name);
}
