package com.project.microservices.app.bus.item.service;

import com.project.microservices.library.commons.model.entity.item.Item;
import com.project.microservices.library.commons.model.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IitemService {

    Optional<Page<Item>> page(Pageable pageable);

    Optional<List<Item>> all();

    Optional<Item> find(Long id, Integer quantity);

    Optional<Boolean> exists(Long id);

    Optional<Item> save(Product product);

    Optional<Item> save(Product product, Long id);

    void delete(Long id);

    Optional<Item> findByName(String name, Integer quantity);
}
