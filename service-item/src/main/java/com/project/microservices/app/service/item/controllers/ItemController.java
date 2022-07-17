package com.project.microservices.app.service.item.controllers;

import com.project.microservices.library.commons.models.entity.item.Item;
import com.project.microservices.library.commons.models.entity.product.Product;
import com.project.microservices.app.service.item.service.IitemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


/**
 * Permite refrescar los componentes, clases, etc, que inyecta con value y configuraciones en tiempo real.
 */
@RefreshScope
@RestController
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private Environment env;

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("ItemServiceFeign")
    //@Qualifier("ItemServiceRestTemplate")
    private IitemService iItemService;

    @Value("${configuration.text}")
    private String texto;

    public ItemController() {
    }


    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name = "name", required = false) String name, @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println(name);
        System.out.println(token);
        return iItemService.findAll();
    }

    @GetMapping("/ver/{id}/quantity/{quantity}")
    /*
     * name: Es el nombre del cortocircuito
     * fallbackMethod: Es el nombre del metodo alternativo
     */
    @CircuitBreaker(name = "service-item-CB", fallbackMethod = "fallbackDetalle")
    public Item detalle(@PathVariable Long id, @PathVariable Integer quantity) {
        //Se cea un cortocircuito llamado service-item-CB
        return iItemService.findById(id, quantity);
    }

    @GetMapping("/ver3/{id}/quantity/{quantity}")
    @CircuitBreaker(name = "service-item-CB", fallbackMethod = "fallbackDetalleList")
    @TimeLimiter(name = "service-item-CB")
    public CompletableFuture<Item> detalleList(@PathVariable Long id, @PathVariable Integer quantity) {
        //Envolmemos el métod en una llamada futura asincrona para calcular el timpo de espera.
        return CompletableFuture.supplyAsync(() -> iItemService.findById(id, quantity));
    }

    /***
     * Se llama cuando falle el metodo detalleList (debe estar en la misma clase)
     * @param id Long
     * @param quantity Integer
     * @param e Throwable
     * @return CompletableFuture<Item>
     */
    public CompletableFuture<Item> fallbackDetalleList(Long id, Integer quantity, Throwable e) {
        logger.info(e.getMessage());

        Item item = new Item();
        Product product = new Product();

        item.setQuantity(quantity);
        product.setId(id);
        product.setName("Camara Sony");
        product.setPrice(500.00);
        item.setProduct(product);
        return CompletableFuture.supplyAsync(() -> item);
    }

    /***
     * Se llama cuando falle el metodo detalle (debe estar en la misma clase)
     * @param id Long
     * @param quantity Integer
     * @param e Throwable
     * @return Item
     */
    public Item fallbackDetalle(Long id, Integer quantity, Throwable e) {
        logger.info(e.getMessage());

        Item item = new Item();
        Product product = new Product();

        item.setQuantity(quantity);
        product.setId(id);
        product.setName("Camara Sony");
        product.setPrice(500.00);
        item.setProduct(product);
        return item;
    }

    @GetMapping("obtener-config")
    public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {
        logger.info(texto);

        Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("puerto", puerto);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.name", env.getProperty("configuration.autor.name"));
            json.put("autor.email", env.getProperty("configuration.autor.email"));

        }
        //return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Product crear(@RequestBody Product product) {
        return iItemService.save(product);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product editar(@RequestBody Product product, @PathVariable Long id) {
        return iItemService.update(product, id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        iItemService.delete(id);
    }
}

