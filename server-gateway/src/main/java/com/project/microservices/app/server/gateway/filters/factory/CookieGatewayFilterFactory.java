package com.project.microservices.app.server.gateway.filters.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import reactor.core.publisher.Mono;
//import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@Component
//public class CookieGatewayFilterFactory extends AbstractGatewayFilterFactory<CookieGatewayFilterFactory.Configuration>{
public class CookieGatewayFilterFactory {

    private final Logger logger = LoggerFactory.getLogger(CookieGatewayFilterFactory.class);

//	public CookieGatewayFilterFactory() {
//		super(Configuration.class);
//	}

    /**
     * Se puede ordenar con OrderedGatewayFilter
     */
    //@Override
    public GatewayFilter apply(Configuration config) {
        return new OrderedGatewayFilter((exchange, chain) -> {

            logger.info("ejecutando pre gateway filter factory: " + config.message);
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                Optional.ofNullable(config.cookieValue).ifPresent(cookieValue -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, cookieValue).build());
                });

                logger.info("ejecutando post gateway filter factory: " + config.message);

            }));
        }, 2);
    }

    /**
     * Personaliza el nombre del filtro, para no tener que usar el prefijo (...GatewayFilterFactory)
     * en el archivo application.yml.
     */
    //@Override
    public String name() {
        return "cookies";
    }

    /**
     * Devuelve una lista con el nombre de campos ordenados como hay que seguir en el application.yml.
     */
    //@Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("message", "cookieName", "cookieValue");
    }

    public static class Configuration {

        private String message;
        private String cookieValue;
        private String cookieName;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCookieValue() {
            return cookieValue;
        }

        public void setCookieValue(String cookieValue) {
            this.cookieValue = cookieValue;
        }

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }


    }

}
