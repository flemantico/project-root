package com.project.microservices.app.core.product.model.dao;

import com.project.microservices.library.commons.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import static com.project.microservices.library.commons.constants.PathsCore.*;

/**
 * RepositoryRestResource: Define el comportamiento rest. El resurso brinda soporte hipermedia.
 * collectionResourceRel y path por defecto son igual al nombre del recurso, en minúsculas y plural, por ejemplo "products"
 * RestResource: sirve para personalizar el endpoint.
 * Param: Sirve para personalizar el nombre del parámetro (Un segmento de ruta válido).
 * Query: Sire para generar consultas personalizadas (SQL nativeQuery = true o JPA)
 */
@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductDao extends JpaRepository<Product, Long> {

    /*
    Personalizadas
    Estos métodos se acceden mediante el path "/search".
     */
    @Transactional(readOnly = true)
    @RestResource(path = PATH_CORE_FIND_BY_NAME_EPP)
    Product findByName(String name);

    /*
    http://localhost:8080/students/search/findByFirstName{?firstName,page,size,sort}
    @Transactional(readOnly = true)
    @RestResource(path = FIND_BY_NAME_EPP)
    List<Product> findByName(String name, Pageable pageable);
    */

}
