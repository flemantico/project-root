package com.project.microservices.app.bus.product.client;

import com.project.microservices.library.commons.constants.Status;
import com.project.microservices.library.commons.model.entity.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Consume un servicio de forma remota mediante una interfaz. Lo identifica mediante el nombre de application.properties.
 * Al estar registrado en Eureka ya no hace falta la URL. @FeignClient(name = "bus-product", url="localhost:8089")
 * Siempre se debe agregar search antes del nombre del método para usar los endpoint REST.
 * @author l0513599
 */

@FeignClient(name = "core-product")
//@RequestMapping(PRODUCT_PATH)
public interface ProductFeignClient {

    @GetMapping(path = "/products/pages")
    Optional<Page<Product>> pages(Pageable pageable);

    @GetMapping(path = "/products")
    Optional<List<Product>> all();

    @GetMapping(path = "/products/{id}")
    Optional<Product> find(@PathVariable Long id);

    @GetMapping(path = "/products/{id}")
    Optional<Boolean> exists(@PathVariable  Long id);

    @PostMapping(path = "/products")
    Optional<Product> save(@RequestBody Product product);

    @PutMapping(path = "/products/{id}")
    Optional<Product> save(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping(path = "/products/{id}")
    void delete(@PathVariable Long id);

    @GetMapping(path = "/products/search/find-by-name")
    Optional<Product> findByName(@RequestParam String name);

    @GetMapping(path = "/products/search/find-by-status")
    Optional<List<Product>> findByStatus(@RequestParam Status status);
}
