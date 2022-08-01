package com.project.microservices.app.server.oauth.services;

import com.project.microservices.library.commons.models.entity.user.User;

public interface IUserService {
    User findById(Long id);

    User findByUsername(String username);

    User update(User user, Long id);
}
