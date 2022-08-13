
package com.project.microservices.app.bus.product.controller;

import com.project.microservices.app.bus.product.service.ProductService;
import com.project.microservices.library.commons.constants.*;
import com.project.microservices.library.commons.model.entity.http.ResponseClass;
import com.project.microservices.library.commons.model.entity.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
//import java.awt.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

import java.util.List;

import static com.project.microservices.library.commons.constants.Verbs.*;
import static com.project.microservices.library.commons.utils.GlobalsFunctions.*;

/**
 * Product controller
 * Permite refrescar los componentes, clases, etc., que inyecta con value y configuraciones en tiempo real.
 */
@RefreshScope
@RestController
public class ProductController {
    private static final Integer DIFFERENCE_TIME_IN_MINUTE = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private Environment env;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    @Qualifier("ProductServiceImpFeign")
    private ProductService productService;



    private HttpStatus httpStatus = HttpStatus.OK;

    @GetMapping(value = ALL_OBJECTS_PAGES)
    public ResponseEntity<ResponseClass> page(
            @RequestParam (required = false, defaultValue = "0") int page,
            @RequestParam (required = false, defaultValue = "10") int size,
            @RequestParam (required = false, defaultValue = "id") String column,
            @RequestParam (required = false, defaultValue = "true") boolean isAscending, HttpServletRequest httpServletRequest){
        LOGGER.info(GET_ALL);
        ResponseClass response = new ResponseClass(httpServletRequest, OBJECT_BY_NAME, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Page<Product> pages;
            pages = productService.page(PageRequest.of(page, size, Sort.by((isAscending ? Sort.Direction.ASC : Sort.Direction.DESC), column))).orElse(null);
            verifyIsFoundEmptyResponse(pages, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = ALL_OBJECTS)
    public ResponseEntity<ResponseClass> all(HttpServletRequest httpServletRequest) {
        LOGGER.info(GET_ALL);
        ResponseClass response = new ResponseClass(httpServletRequest, ALL_OBJECTS, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            List<Product> products = productService.all().orElse(null);
            verifyIsFoundEmptyResponse(products, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = OBJECT_BY_ID)
    public ResponseEntity<ResponseClass> get(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        LOGGER.info(GET_BY_ID);
        ResponseClass response = new ResponseClass(httpServletRequest, OBJECT_BY_ID, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Product product = productService.find(id).orElse(null);
            verifyIsFoundEmptyResponse(product, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = OBJECT_FIND_BY_NAME)
    public ResponseEntity<ResponseClass> findByName(@PathVariable String name, HttpServletRequest httpServletRequest) {
        LOGGER.info(GET_BY_NAME);
        ResponseClass response = new ResponseClass(httpServletRequest, OBJECT_FIND_BY_NAME, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Product product = productService.findByName(name).orElse(null);
            verifyIsFoundEmptyResponse(product, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(ALL_OBJECTS)
    public ResponseEntity<ResponseClass> save(@RequestBody Product product, HttpServletRequest httpServletRequest) {
        LOGGER.info(CREATE);
        ResponseClass response = new ResponseClass(httpServletRequest, ALL_OBJECTS, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Product productReturn = productService.save(product).orElse(null);
            verifyIsFoundEmptyResponse(productReturn, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping(OBJECT_BY_ID)
    public ResponseEntity<ResponseClass> save(@RequestBody Product product, @PathVariable Long id, HttpServletRequest httpServletRequest) {
        LOGGER.info(EDIT);
        ResponseClass response = new ResponseClass(httpServletRequest, OBJECT_BY_ID, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Product productDb = productService.find(id).orElse(null);
            if (HttpStatus.CREATED.equals(verifyIsFoundEmptyResponse(productDb, response))){
                String[] noTargetMethod = {"createdAt"};
                copyAvailableFields(product, productDb, noTargetMethod);
                productService.save(productDb);
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping(OBJECT_BY_ID)
    public ResponseEntity<ResponseClass> delete(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        LOGGER.info(DELETE);
        ResponseClass response = new ResponseClass(httpServletRequest, OBJECT_BY_ID, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Product product = productService.find(id).orElse(null);
            if (HttpStatus.NO_CONTENT.equals(verifyIsFoundEmptyResponse(product, response))){
                productService.delete(id);
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Automatic task that changes the status to expired of products whose expiration date has expired.
     */
//    @Scheduled(initialDelayString = "${update.expired.product.fixedDelay}", fixedDelayString = "${update.expired.product.fixedDelay}")
//    public void updateExpirationProductInstances() {
//        //final List<Product> pendingList = productService.findAllByStatus(Status.AUTHORIZED);
//        final List<Product> pendingList = productService.findAll();
//
//        if (isNotNull(pendingList) && !pendingList.isEmpty()) {
//            LOGGER.info("updateInstances - " + OBJECT + " : {}", sanitize(pendingList.size()));
//
//            for (Product dto : pendingList) {
//                try {
//                    LocalDateTime dateTimeExpiration = LocalDateTime.parse(dto.getExpirationOn().toString(), DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS"));
//
//                    if (dateTimeExpiration.isBefore(LocalDateTime.now().minusMinutes(DIFFERENCE_TIME_IN_MINUTE))) {
//                        dto.setStatus(Status.EXPIRED);
//                        productService.save(dto);
//                    }
//                } catch (Exception e) {
//                    LOGGER.error("Error al invocar updateExpirationProductInstances", sanitize(e));
//                }
//            }
//        }
//    }


	/*@GetMapping("/ver/{id}")
	public Product detalle(@PathVariable Long id) throws InterruptedException {
		//Simulamos un error para el id 10.
		if(id.equals(10L)) {
			throw new IllegalStateException("Product no encontrado");
		}
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5);
		}

		//Product product = productService.findById(id);
		Product product = productService.findById(id).orElse(null);
		product.setPort(getPort());
		//product.setPort(port);

		*//*
		try {
			 //Time-out 2'', por defecto es de 1''
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*//*
		return product;
	}*/
}
