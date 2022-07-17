
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
    public static final Integer DIFFERENCE_TIME_IN_MINUTE = 5;

    //@Autowired
    //private ModelMapper modelMapper;
    static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final String OBJECT = "/products";
    private final String OBJECT_ALL = OBJECT + ROOT;
    private final String OBJECT_ID = OBJECT + ID;
    private final String OBJECT_NAME = OBJECT + NAME;
    @Autowired
    private Environment env;
    @Value("${server.port}")
    private Integer port;
    @Autowired
    private IProductService productService;

    HttpStatus httpStatus;
	
	/*@GetMapping("/listar")
	public List<Product> listar(){
		return productService.findAll().stream().map(product ->{
			//product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			product.setPort(port);
			return product;
		}).collect(Collectors.toList());
	}*/
	
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
		product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
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

    @GetMapping(value = ROOT)
    public ResponseEntity<ResponseClass> list(HttpServletRequest httpServletRequest) {

        LOGGER.info(GET_ALL);
        ResponseClass response = new ResponseClass(HttpMethod.GET, OBJECT_ALL, Integer.parseInt(env.getProperty("local.server.port")));

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

//    @GetMapping(value = ID)
//    public ResponseEntity<ResponseClass> get(@PathVariable Long id) {
//        LOGGER.info(GET_BY_ID);
//        ResponseClass response = new ResponseClass(HttpMethod.GET, OBJECT_ID, Integer.parseInt(env.getProperty("local.server.port")));
//
//        try {
//            httpStatus = HttpStatus.OK;
//            Product product = productService.findById(id).orElse(null);
//            verifyIsFoundEmptyResponse(product, response);
//        } catch (Exception e) {
//            httpStatus = HttpStatus.CONFLICT;
//            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
//        }
//
//        LOGGER.info("Response: {}", sanitize(response));
//        return new ResponseEntity<>(response, httpStatus);
//    }


    @GetMapping(value = ID)
    public Product get(@PathVariable Long id) {
        LOGGER.info(GET_BY_ID);
        Product product = null;
        try {
            httpStatus = HttpStatus.OK;
            product = productService.findById(id).orElse(null);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), null));
        }

        LOGGER.info("Response: {}", product);
        return product;
    }

    @GetMapping(value = NAME)
    public ResponseEntity<ResponseClass> get(@PathVariable String name) {
        LOGGER.info(GET_BY_NAME);
        ResponseClass response = new ResponseClass(HttpMethod.GET, OBJECT_NAME, Integer.parseInt(env.getProperty("local.server.port")));

        try {
            httpStatus = HttpStatus.OK;
            Product product = productService.findByName(name).orElse(null);
            verifyIsFoundEmptyResponse(product, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(ROOT)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseClass> create(@RequestBody Product product) {
        LOGGER.info(CREATE);
        ResponseClass response = new ResponseClass(HttpMethod.POST, OBJECT, Integer.parseInt(env.getProperty("local.server.port")));

        httpStatus = HttpStatus.OK;
        Product productReturn = productService.save(product);
        verifyIsFoundEmptyResponse(productReturn, response);

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping(ID)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseClass> edit(@RequestBody Product product, @PathVariable Long id) {
        LOGGER.info(EDIT);
        ResponseClass response = new ResponseClass(HttpMethod.PUT, OBJECT, Integer.parseInt(env.getProperty("local.server.port")));

        try {
            httpStatus = HttpStatus.OK;
            Product productDb = productService.findById(id).orElse(null);
            verifyIsFoundEmptyResponse(product, response);
            String[] noTargetMethod = {"createdAt"};
            copyAvailableFields(product, productDb, noTargetMethod);
            Product productSaved = productService.save(productDb);
            verifyIsFoundEmptyResponse(productSaved, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }

        LOGGER.info("Response: {}", sanitize(response));
        return new ResponseEntity<>(response, httpStatus);




		/*Product productDb = productService.findById(id).orElse(null);
		verifyIsFoundEmptyResponse(product, response);
		if (!isEmpty(productDb)){
			String[] noTargetMethod = {"createdAt"};
			copyAvailableFields(product, productDb, noTargetMethod);
			Product productSaved = productService.save(productDb);
			verifyIsFoundEmptyResponse(productSaved, response);
		}*/

        //return ResponseEntity.ok(response);
    }

    //@PostMapping(ROOT)
    //@ResponseStatus(HttpStatus.CREATED)
    //public Product create2(@RequestBody Product product) {
    //	LOGGER.info(CREATE);
    //	return productService.save(product);
    //}
	
/*	@PutMapping(ID)
	@ResponseStatus(HttpStatus.CREATED)
	public Product edit(@RequestBody Product product, @PathVariable Long id) {
		LOGGER.info(EDIT);
		Product productDb = productService.findById(id).orElse(null);
		
		productDb.setName(product.getName());
		productDb.setPrice(product.getPrice());
		
		return productService.save(productDb);
	}*/

    @DeleteMapping(ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        LOGGER.info(DELETE);
        productService.deleteById(id);
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
