
package com.project.microservices.app.service.product.controllers;

import com.project.microservices.library.commons.constants.*;
import com.project.microservices.library.commons.models.entity.http.ResponseClass;
import com.project.microservices.library.commons.models.entity.product.Product;
import com.project.microservices.app.service.product.models.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.project.microservices.library.commons.constants.Verbs.*;
import static com.project.microservices.library.commons.utils.GlobalsFunctions.*;

/**
 * Product controller
 */
@RestController
public class ProductController {
    private static final Integer DIFFERENCE_TIME_IN_MINUTE = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private static final String OBJECT = "/product";

    @Autowired
    private Environment env;
    @Value("${server.port}")
    private Integer port;

    @Autowired
    private IProductService productService;

    private HttpStatus httpStatus;

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

    @GetMapping(value = ALL_OBJECTS_PAGES)
    public ResponseEntity<ResponseClass> findAll(
            @RequestParam (required = false, defaultValue = "0") int page,
            @RequestParam (required = false, defaultValue = "10") int size,
            @RequestParam (required = false, defaultValue = "id") String column,
            @RequestParam (required = false, defaultValue = "true") boolean isAscending, HttpServletRequest httpServletRequest){
        LOGGER.info(GET_ALL);
        ResponseClass response = new ResponseClass(HttpMethod.GET, OBJECT_BY_NAME, getPort(env));

        try {
            httpStatus = HttpStatus.OK;
            Page<Product> pages;
            if(!isAscending){
                pages = productService.findAll(PageRequest.of(page, size, Sort.by(column).descending()));
            }else{
                pages = productService.findAll(PageRequest.of(page, size, Sort.by(column)));
            }
            verifyIsFoundEmptyResponse(pages, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = ALL_OBJECTS)
    public ResponseEntity<ResponseClass> findAll(HttpServletRequest httpServletRequest) {
        LOGGER.info(GET_ALL);
        ResponseClass response = new ResponseClass(HttpMethod.GET, ALL_OBJECTS, getPort(env));

        try {
            httpStatus = HttpStatus.OK;
            List<Product> products = productService.findAll();
            verifyIsFoundEmptyResponse(products, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = OBJECT_BY_ID)
    public ResponseEntity<ResponseClass> get(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        LOGGER.info(GET_BY_ID);
        ResponseClass response = new ResponseClass(HttpMethod.GET, OBJECT_BY_ID, getPort(env));

        try {
            httpStatus = HttpStatus.OK;
            Product product = productService.findById(id).orElse(null);
            verifyIsFoundEmptyResponse(product, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = OBJECT_BY_NAME)
    public ResponseEntity<ResponseClass> findByName(@PathVariable String name, HttpServletRequest httpServletRequest) {
        LOGGER.info(GET_BY_NAME);
        ResponseClass response = new ResponseClass(HttpMethod.GET, OBJECT_BY_NAME, getPort(env));

        try {
            httpStatus = HttpStatus.OK;
            Product product = productService.findByName(name).orElse(null);
            verifyIsFoundEmptyResponse(product, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(ALL_OBJECTS)
    public ResponseEntity<ResponseClass> save(@RequestBody Product product, HttpServletRequest httpServletRequest) {
        LOGGER.info(CREATE);
        ResponseClass response = new ResponseClass(HttpMethod.POST, ALL_OBJECTS, getPort(env));

        try {
            httpStatus = HttpStatus.OK;
            Product productReturn = productService.save(product);
            verifyIsFoundEmptyResponse(productReturn, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping(OBJECT_BY_ID)
    public ResponseEntity<ResponseClass> save(@RequestBody Product product, @PathVariable Long id, HttpServletRequest httpServletRequest) {
        LOGGER.info(EDIT);
        ResponseClass response = new ResponseClass(HttpMethod.PUT, OBJECT_BY_ID, getPort(env));

        try {
            httpStatus = HttpStatus.OK;
            Product productDb = productService.findById(id).orElse(null);
            if (HttpStatus.FOUND.equals(verifyIsFoundEmptyResponse(productDb, response))){
                String[] noTargetMethod = {"createdAt"};
                copyAvailableFields(product, productDb, noTargetMethod);
                productService.save(productDb);
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping(value = OBJECT_BY_ID)
    public ResponseEntity<ResponseClass> delete(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        LOGGER.info(DELETE);
        ResponseClass response = new ResponseClass(HttpMethod.DELETE, OBJECT_BY_ID, getPort(env));

        try {
            httpStatus = HttpStatus.OK;
            Product product = productService.findById(id).orElse(null);
            if (HttpStatus.NO_CONTENT.equals(verifyIsFoundEmptyResponse(product, response))){
                productService.deleteById(id);
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            setResponse(response, httpServletRequest);
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Automatic task that changes the status to expired of products whose expiration date has expired.
     */
    @Scheduled(initialDelayString = "${update.expired.product.fixedDelay}", fixedDelayString = "${update.expired.product.fixedDelay}")
    public void updateExpirationProductInstances() {
        //final List<Product> pendingList = productService.findAllByStatus(Status.AUTHORIZED);
        final List<Product> pendingList = productService.findAll();

        if (isNotNull(pendingList) && !pendingList.isEmpty()) {
            LOGGER.info("updateInstances - " + OBJECT + " : {}", sanitize(pendingList.size()));

            for (Product dto : pendingList) {
                try {
                    LocalDateTime dateTimeExpiration = LocalDateTime.parse(dto.getExpirationOn().toString(), DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS"));

                    if (dateTimeExpiration.isBefore(LocalDateTime.now().minusMinutes(DIFFERENCE_TIME_IN_MINUTE))) {
                        dto.setStatus(Status.EXPIRED);
                        productService.save(dto);
                    }
                } catch (Exception e) {
                    LOGGER.error("Error al invocar updateExpirationProductInstances", sanitize(e));
                }
            }
        }
    }
}
