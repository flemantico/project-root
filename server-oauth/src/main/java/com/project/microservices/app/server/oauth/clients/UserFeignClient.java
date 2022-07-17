package com.project.microservices.app.server.oauth.clients;

import com.project.microservices.library.commons.models.entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "service-user")
public interface UserFeignClient {

    @GetMapping("/users/search/buscar-username")
    User findByUsername(@RequestParam String username);

    @PutMapping("/users/{id}")
    User update(@RequestBody User user, @PathVariable Long id);
}
