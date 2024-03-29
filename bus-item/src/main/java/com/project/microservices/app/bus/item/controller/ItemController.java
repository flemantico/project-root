package com.project.microservices.app.bus.item.controller;

import com.project.microservices.library.commons.constants.Errors;
import com.project.microservices.library.commons.model.entity.http.ResponseClass;
import com.project.microservices.library.commons.model.entity.item.Item;
import com.project.microservices.library.commons.model.entity.product.Product;
import com.project.microservices.app.bus.item.service.IitemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.microservices.library.commons.constants.Messages.MESSAGE_ERROR;
import static com.project.microservices.library.commons.constants.Messages.MESSAGE_RESPONSE;
import static com.project.microservices.library.commons.constants.Verbs.*;
import static com.project.microservices.library.commons.constants.PathsBus.*;
import static com.project.microservices.library.commons.utils.GlobalsFunctions.*;

/**
 * Permite refrescar los componentes, clases, etc., que inyecta con value y configuraciones en tiempo real.
 */
@RefreshScope
@RestController
public class ItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private Environment env;

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("ItemServiceFeign")
    private IitemService iItemService;

    @Value("${configuration.text}")
    private String texto;

    private HttpStatus httpStatus = HttpStatus.OK;

    @GetMapping(value = PATH_BUS_ALL_OBJECTS_PAGES)
    public ResponseEntity<ResponseClass> page(
            @RequestParam (required = false, defaultValue = "0") int page,
            @RequestParam (required = false, defaultValue = "10") int size,
            @RequestParam (required = false, defaultValue = "id") String column,
            @RequestParam (required = false, defaultValue = "true") boolean isAscending, HttpServletRequest httpServletRequest){
        LOGGER.info(METHOD_GET_ALL);
        ResponseClass response = new ResponseClass(httpServletRequest, PATH_BUS_OBJECT_BY_NAME, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Page<Item> pages;
            pages = iItemService.page(PageRequest.of(page, size, Sort.by((isAscending ? Sort.Direction.ASC : Sort.Direction.DESC), column))).orElse(null);
            verifyIsFoundEmptyResponse(pages, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            //LOGGER.info(MESSAGE_ERROR, createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        //LOGGER.info(MESSAGE_RESPONSE, sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = PATH_BUS_ALL_OBJECTS)
    public ResponseEntity<ResponseClass> all(HttpServletRequest httpServletRequest) {
        LOGGER.info(METHOD_GET_ALL);
        ResponseClass response = new ResponseClass(httpServletRequest, PATH_BUS_ALL_OBJECTS, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            List<Item> items = iItemService.all().orElse(null);
            verifyIsFoundEmptyResponse(items, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            //LOGGER.info(MESSAGE_ERROR, createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        //LOGGER.info(MESSAGE_RESPONSE, sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }


    @GetMapping("/find/{id}/quantity/{quantity}")
    public ResponseEntity<ResponseClass> get(@PathVariable Long id, @PathVariable Integer quantity, HttpServletRequest httpServletRequest) {
        LOGGER.info(METHOD_GET);
        ResponseClass response = new ResponseClass(httpServletRequest, PATH_BUS_OBJECT_BY_ID, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Item item = iItemService.find(id, quantity).orElse(null);
            verifyIsFoundEmptyResponse(item, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            //LOGGER.info(MESSAGE_ERROR, createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        //LOGGER.info(MESSAGE_RESPONSE, sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(PATH_BUS_ALL_OBJECTS)
    public ResponseEntity<ResponseClass> save(@RequestBody Product product, HttpServletRequest httpServletRequest) {
        LOGGER.info(METHOD_CREATE);
        ResponseClass response = new ResponseClass(httpServletRequest, PATH_BUS_ALL_OBJECTS, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Item item = iItemService.save(product).orElse(null);
            verifyIsFoundEmptyResponse(item, response);
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            //LOGGER.info(MESSAGE_ERROR, createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        //LOGGER.info(MESSAGE_RESPONSE, sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping(PATH_BUS_OBJECT_BY_ID)
    public ResponseEntity<ResponseClass> save(@RequestBody Product product, @PathVariable Long id, HttpServletRequest httpServletRequest) {
        LOGGER.info(METHOD_EDIT);
        ResponseClass response = new ResponseClass(httpServletRequest, PATH_BUS_OBJECT_BY_ID, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Item item = iItemService.find(id, 1).orElse(null);
            if (HttpStatus.CREATED.equals(verifyIsFoundEmptyResponse(item, response))){
                String[] noTargetMethod = {"createdAt"};
                copyAvailableFields(product, item, noTargetMethod);
                iItemService.save(product, id);
            }
        } catch (Exception e) {
             httpStatus = HttpStatus.CONFLICT;
            //LOGGER.info(MESSAGE_ERROR, createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        //LOGGER.info(MESSAGE_ERROR, sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping(PATH_BUS_OBJECT_BY_ID)
    public ResponseEntity<ResponseClass> delete(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        LOGGER.info(METHOD_DELETE);
        ResponseClass response = new ResponseClass(httpServletRequest, PATH_BUS_OBJECT_BY_ID, getPort(env));
        httpStatus = HttpStatus.OK;

        try {
            Item item = iItemService.find(id, 1).orElse(null);
            if (HttpStatus.NO_CONTENT.equals(verifyIsFoundEmptyResponse(item, response))){
                iItemService.delete(id);
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.CONFLICT;
            //LOGGER.info(MESSAGE_ERROR, createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
        }finally{
            //setResponse(response, httpServletRequest);
        }

        //LOGGER.info(MESSAGE_RESPONSE, sanitize(response));
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("obtener-config")
    public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {
        LOGGER.info(texto);
        httpStatus = HttpStatus.OK;

        Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("puerto", puerto);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.name", env.getProperty("configuration.autor.name"));
            json.put("autor.email", env.getProperty("configuration.autor.email"));

        }
        //return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
        return new ResponseEntity<>(json, httpStatus);
    }
}

