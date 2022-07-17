package com.project.microservices.app.server.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JWTAuthenticationFilter implements WebFilter {

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //Convertimos en el token de la cabecera http en un Mono (flujo reactivo)
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                //Preguntamos si contiene Bearer
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                //Si no lo contiene se sale del flujo y se devuelve Empty
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                //Si contien el Bearer se quita el Bearer. Se usa map, porque se usa un String.
                .map(token -> token.replace("Bearer ", ""))
                //Se usa flatMap, porque se devuelve un Mono (flijo).
                .flatMap(token -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(null, token)))
                //Se emite el authnetication y se guarda en el contexto la autenticación con el token validado.
                .flatMap(authentication -> chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
    }
}
