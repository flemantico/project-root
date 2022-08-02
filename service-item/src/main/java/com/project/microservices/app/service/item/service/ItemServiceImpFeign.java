package com.project.microservices.app.service.item.service;

import com.project.microservices.library.commons.model.entity.item.Item;
import com.project.microservices.library.commons.model.entity.product.Product;
import com.project.microservices.app.service.item.client.ProductFeignClient;

import feign.FeignException;
import feign.codec.DecodeException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/***
 * This is a new way of communication between microservices.
 */
@Service("ItemServiceFeign")
public class ItemServiceImpFeign implements IitemService {
    static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpFeign.class);

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    @Transactional(readOnly = true)
    public Optional<Page<Item>> page(Pageable pageable) {

        Page<Product> page = productFeignClient.pages(pageable).get();

        return Optional.of((Page<Item>) page.stream().map(p -> new Item(p, 1)).collect(Collectors.toList()));

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Item>> all() {


        try {
            List<Product> list = productFeignClient.all().get();

            return Optional.of(list.stream().map(p -> new Item(p, 1)).collect(Collectors.toList()));
        } catch (FeignException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new DecodeException(1, e.getMessage(), null);
        }
    }

    @Override
    @CircuitBreaker(name = "service-item-CB", fallbackMethod = "fallbackDetalle")
    @Transactional(readOnly = true)
    public Optional<Item> find(Long id, Integer quantity) {
        return Optional.of(new Item(productFeignClient.find(id).get(), quantity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Boolean> exists(Long id) {
        return productFeignClient.exists(id);
    }

    @Override
    @Transactional
    public Optional<Item> save(Product product) {
        return Optional.of(new Item(productFeignClient.save(product).get(),1));
    }

    @Override
    @Transactional
    public Optional<Item> save(Product product, Long id) {
        return Optional.of(new Item(productFeignClient.save(product, id).get(),1));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productFeignClient.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Item> findByName(String name, Integer quantity) {
        return Optional.of(new Item(productFeignClient.findByName(name).get(), quantity));
    }

    @CircuitBreaker(name = "service-item-CB", fallbackMethod = "fallbackDetalleList")
    @TimeLimiter(name = "service-item-CB")
    @Transactional(readOnly = true)
    public CompletableFuture<Optional<Item>> detalleList(Long id, Integer quantity) {
        //Envolmemos el métod en una llamada futura asincrona para calcular el timpo de espera.
        return CompletableFuture.supplyAsync(() -> Optional.of(new Item(productFeignClient.find(id).get(), quantity)));
    }

    /***
     * Se llama cuando falle el metodo detalle (debe estar en la misma clase)
     * @param id Long
     * @param quantity Integer
     * @param e Throwable
     * @return Optional.of(item)
     */
    @SuppressWarnings("unused")
    public Optional<Item> fallbackDetalle(Long id, Integer quantity, Throwable e) {
        //LOGGER.info(e.getCause().getMessage());


        Item item = createItrem(id, quantity);

        return Optional.of(item);
    }

    /***
     * Se llama cuando falle el metodo detalleList (debe estar en la misma clase)
     * @param id Long
     * @param quantity Integer
     * @param e Throwable
     * @return CompletableFuture<Optional.of(item)>
     */
    @SuppressWarnings("unused")
    public CompletableFuture<Optional<Item>> fallbackDetalleList(Long id, Integer quantity, Throwable e) {
        LOGGER.info(e.getMessage());

        Item item = createItrem(id, quantity);

        return CompletableFuture.supplyAsync(() -> Optional.of(item));
    }


    private Item createItrem(Long id, Integer quantity){
        Item item = new Item();
        Product product = new Product();

        item.setQuantity(quantity);
        product.setId(id);
        product.setName("Producto no encontrado");
        item.setProduct(product);
        return item;
    }
}
