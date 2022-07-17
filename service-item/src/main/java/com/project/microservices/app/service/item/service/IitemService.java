package com.project.microservices.app.service.item.service;

import com.project.microservices.library.commons.models.entity.item.Item;
import com.project.microservices.library.commons.models.entity.product.Product;

import java.util.List;

public interface IitemService {

    List<Item> findAll();

    Item findById(Long id, Integer quantity);

    //ResponseEntity<ResponseClass> findById(Long id, Integer quantity);

    Product save(Product product);

    Product update(Product product, Long id);

    void delete(Long id);
}
