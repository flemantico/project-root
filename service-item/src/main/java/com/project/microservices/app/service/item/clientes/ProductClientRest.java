package com.project.microservices.app.service.item.clientes;

import com.project.microservices.library.commons.models.entity.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Consume un servicio de forma remota mediante una interfaz. Lo identifica mediante el nombre de application.properties.
 * Al estar registrado en Eureka ya no hace falta la URL. @FeignClient(name = "service-product", url="localhost:8089")
 *
 * @author l0513599
 */
@FeignClient(name = "service-product")
public interface ProductClientRest {

    @GetMapping("/")
    List<Product> listar();

    @GetMapping("/{id}")
    Product findById(@PathVariable Long id);

//    @GetMapping("/{id}")
//    ResponseEntity<ResponseClass> findById(@PathVariable Long id);


    @PostMapping("/")
    Product crear(@RequestBody Product product);

    @PutMapping("/{id}")
    Product editar(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void eliminar(@PathVariable Long id);
}
