package com.project.microservices.app.service.user.models.dao;

import com.project.microservices.library.commons.models.entity.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * RepositoryRestResource: Define el comportamiento rest. El resurso brinda soporte hipermedia.
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserDao extends JpaRepository<User, Long> {
    //@Query("select u from User u where u.username = ?1 and u.email = ?2")
    //User findByUsernameAndEmail(String username, String email);

    /*
    @RestResource: sirve para personalizar el endpoint
    @Param: Sirve para personalizar el nombre del parámetro (Un segmento de ruta válido).
     */
    @RestResource(path = "buscar-username")
    User findByUsername(@Param("username") String username);

    //Desde las clases
    @Query("select u from User u where u.username = ?1")
    //Para hacer la query nativa de SQL
    //@Query(value="select u from users u where u.username = ?1", nativeQuery = true)
    User obtenerPorUsername(String username);


}
