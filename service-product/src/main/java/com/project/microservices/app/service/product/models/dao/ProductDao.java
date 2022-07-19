package com.project.microservices.app.service.product.models.dao;

import com.project.microservices.library.commons.constants.Status;
import com.project.microservices.library.commons.models.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

//@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductDao extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    List<Product> findAllByStatus(Status status);

    Optional<Product> findByName(String username);

//    @RestResource(path = "buscar-name")
//    Optional<Product> findByName(@Param("username") String username);
}
