package com.project.microservices.app.server.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Con la anotacion @EnableWebFluxSecurity tendremos un método Bean que va a registrar un componente SecurityWebFilterChain, para configurar la seguridad.
 */
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JWTAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                //api/users: path de gateway.
                //users: path de @RepositoryRestResource.
//                .pathMatchers(HttpMethod.GET, "/api/users/users/**",
//                        "/api/core-products/products/**",
//                        "/api/core-products/products/{id}"
//                        "/api/bus-products/products/**",
//                        "/api/bus-products/products/{id}"
//                        "/api/bus-items/**",
//                        "/api/bus-items/{id}/quantity/{cantidad}",
//                ).permitAll()
//                .pathMatchers(HttpMethod.GET, "/api/users/users/{id}").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/api/users/users/**",
                        "/api/core-products/products/**",
                        "/api/bus-products/products/**",
                        "/api/bus-items/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                //Agregar el filtro de autenticacion
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                //Se debe deshabilitar la seguridad CSRF (Cross Site Request Forgery), porque no trabajamos con Thymeleaf
                .csrf().disable()
                .build();
    }
}
