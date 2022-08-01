package com.project.microservices.app.server.oauth.client;

import com.project.microservices.library.commons.models.entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "service-user")
public interface UserFeignClient {

    @GetMapping("/users/search/find-by-username")
    Optional<User> findByUsername(@RequestParam String username);

    //TODO: no funciona
    @GetMapping("/users/search/find-by-id")
    Optional<User> findById(@RequestParam Long id);

    @PutMapping("/users/{id}")
    Optional<User> update(@RequestBody User user, @PathVariable Long id);
}
