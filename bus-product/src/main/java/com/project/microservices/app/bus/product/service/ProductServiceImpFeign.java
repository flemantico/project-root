package com.project.microservices.app.bus.product.service;

import com.project.microservices.app.bus.product.client.ProductFeignClient;
import com.project.microservices.library.commons.constants.Status;
import com.project.microservices.library.commons.model.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/***
 * This is a new way of communication between microservices.
 */
@Service("ProductServiceImpFeign")
public class ProductServiceImpFeign implements ProductService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    @Transactional(readOnly = true)
    public Optional<Page<Product>> page(Pageable pageable) {
        return productFeignClient.pages(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> find(Long id) {
        return productFeignClient.find(id);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public boolean exists(Long id) {
//        return productFeignClient.exists(id);
//    }

    @Override
    @Transactional
    public Optional<Product> save(Product product) {
        return productFeignClient.save(product);
    }

    @Override
    public Optional<Product> save(Product product, Long id) {
        return productFeignClient.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productFeignClient.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return productFeignClient.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findByName(String name) {
        return productFeignClient.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Product>> findAllByStatus(Status status) {
        return productFeignClient.findByStatus(status);
    }
}
