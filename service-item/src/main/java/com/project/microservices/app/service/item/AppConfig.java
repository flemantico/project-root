package com.project.microservices.app.service.item;

//import java.time.Duration;

//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
//import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.timelimiter.TimeLimiterConfig;

//Se configura el circuitbracker por código
@Configuration
public class AppConfig {

    @Bean("ProductFeignClient")
    @LoadBalanced
    public RestTemplate registrarRestTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
//        //id es el cortocircuito "items" emitido. Se debe configurar por cada uno. En este caso solo tenemos uno.
//        return factory -> factory.configureDefault(id -> {
//            return new Resilience4JConfigBuilder(id)
//                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
//                            .slidingWindowSize(10) // por defecto 100
//                            .failureRateThreshold(50) // por defecto 50
//                            .waitDurationInOpenState(Duration.ofSeconds(10L)) // por defecto 60'
//                            .permittedNumberOfCallsInHalfOpenState(5) // por defecto 10
//                            //Cuando el umbral de llamadas lentas es igual o mayor al umbral (50) se pasa a estado abierto.
//                            .slowCallRateThreshold(50) // por defecto 100
//                            //Configurar la duración de la llamada lenta. Si se superan los 2' se considera lenta.
//                            .slowCallDurationThreshold(Duration.ofSeconds(2L))
//                            .build())
//                    //.timeLimiterConfig(TimeLimiterConfig.ofDefaults())
//                    //Configurar el timeout
//                    .timeLimiterConfig(TimeLimiterConfig.custom()
//                            .timeoutDuration(Duration.ofSeconds(3L))
//                            .build()
//                    )
//                    .build();
//        });
//    }
}
