package com.project.microservices.app.server.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JWTAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.GET,
                        "/api/products",
                        "/api/items/listar",
                        "/api/users/users",
                        "/api/items/ver/{id}/quantity/{cantidad}",
                        "/api/products/{id}").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/users/users/{id}").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/api/products/**", "/api/items/**", "/api/users/users/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                //Agregar el filtro
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                //Se debe deshabilitar la seguridad CSRF (Cross Site Request Forgery), porque no trabajamos con Thymeleaf
                .csrf().disable()
                .build();
    }
}
