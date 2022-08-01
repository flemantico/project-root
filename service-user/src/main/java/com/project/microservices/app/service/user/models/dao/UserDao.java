package com.project.microservices.app.service.user.models.dao;

import com.project.microservices.library.commons.models.entity.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * RepositoryRestResource: Define el comportamiento rest. El resurso brinda soporte hipermedia HETOAS.
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserDao extends JpaRepository<User, Long> {
    /*
    @RestResource (org.springframework.data.rest.core.annotation.RestResource):
    Sirve para personalizar el endpoint.
    @Param (import org.springframework.data.repository.query.Param):
    Sirve para personalizar el nombre del parámetro (Un segmento de ruta válido).

    Siempre se debe agregar search antes del nombre del método para usar estos endpoint REST.
     */
    @Transactional(readOnly = true)
    @RestResource(path = "find-by-id")
    Optional<User> findById(Long id);

    @Transactional(readOnly = true)
    @RestResource(path = "find-by-username")
    Optional<User> findByUsername(String username);

    @Transactional(readOnly = true)
    //Desde las clases
    @Query("select u from User u where u.username = ?1")
    //Para hacer la query nativa de SQL
    //@Query(value="select u from users u where u.username = ?1", nativeQuery = true)
    Optional<User> obtenerPorUsername(String username);

    //@Query("select u from User u where u.username = ?1 and u.email = ?2")
    //User findByUsernameAndEmail(String username, String email);

}
