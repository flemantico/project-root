//package com.project.microservices.app.server.gateway.filters;
//
//import org.slf4j.LoggerUtils;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.ResponseCookie;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Optional;
//
////@Component
//public class EjemploGlobalFilter implements GlobalFilter, Ordered {
//    private static final LoggerUtils logger = LoggerFactory.getLogger(EjemploGlobalFilter.class);
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        logger.info("ejecutando filtro pre");
//
//        /*
//         * Modificar el request
//         */
//        exchange.getRequest().mutate().headers(h -> h.add("token", "123456"));
//
//        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//            logger.info("ejecutando filtro post");
//
//            /*
//             * Agregar el valor "token" en la respuesta
//             */
//            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor -> {
//                exchange.getResponse().getHeaders().add("token", valor);
//            });
//
//            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor ->
//                    exchange.getResponse().getHeaders().add("token", valor));
//
//            /*
//             * Modificar él response.
//             */
//            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
//            /*
//             * Para modificar el tipo de la respuesta.
//             */
//            //exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PALIN);
//        }));
//    }
//
//    @Override
//    public int getOrder() {
//        /*
//         * Cuando tiene mucha prioridad (por ejemplo -1) no se pueden escribir datos, es solo lectura.
//         */
//        return 1;
//    }
//
//}
