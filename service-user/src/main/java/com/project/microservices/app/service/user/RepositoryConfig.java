package com.project.microservices.app.service.user;

import com.project.microservices.library.commons.model.entity.user.Role;
import com.project.microservices.library.commons.model.entity.user.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    /**
     * Esta configuración sirve para mostrar los id.
     *
     * @param config RepositoryRestConfiguration
     * @param cors CorsRegistry
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(User.class, Role.class);
    }
}
