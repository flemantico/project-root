package com.project.microservices.app.service.item.service;

import com.project.microservices.library.commons.models.entity.item.Item;
import com.project.microservices.library.commons.models.entity.product.Product;
import com.project.microservices.app.service.item.clientes.ProductClientRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//import static com.project.springboot.app.commons.constants.Verbs.*;
//import static com.project.springboot.app.commons.constants.Verbs.NAME;
//import static com.project.springboot.app.commons.utils.GlobalsFunctions.*;

/***
 * This is a new way of communication between microservices.
 */
@Service("ItemServiceFeign")
public class ItemServiceImpFeign implements IitemService {
//    static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpFeign.class);

    @Autowired
    private ProductClientRest productClientRest;

//    @Autowired
//    private Environment env;

//    private final String OBJECT = "/items";
//    private final String OBJECT_ALL = OBJECT + ROOT;
//    private final String OBJECT_ID = OBJECT + ID;
//    private final String OBJECT_NAME = OBJECT + NAME;

//    HttpStatus httpStatus;

    @Override
    public List<Item> findAll() {
        return productClientRest.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        return new Item(productClientRest.findById(id), quantity);
    }

//    @Override
//    public ResponseEntity<ResponseClass> findById(Long id, Integer quantity) {
//        LOGGER.info(GET_BY_ID);
//        ResponseClass response = new ResponseClass(HttpMethod.GET, OBJECT_ID, getPort());
//
//        try {
//            httpStatus = HttpStatus.OK;
//            Product product =  (Product) productClientRest.findById(id).getBody().getData().get(0).getObject();
//            Item item = new Item(product, quantity);
//            verifyIsFoundEmptyResponse(product, response);
//        } catch (Exception e) {
//            httpStatus = HttpStatus.CONFLICT;
//            LOGGER.info("Error: {}", createError(Errors.TECHNICAL_ERROR_CODE, Errors.TECHNICAL_ERROR_DETAIL + " - " + sanitize(e.getMessage()), response));
//        }
//
//
//        return new ResponseEntity<>(response, httpStatus);
//    }

    @Override
    public Product save(Product product) {
        return productClientRest.crear(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return productClientRest.editar(product, id);
    }

    @Override
    public void delete(Long id) {
        productClientRest.eliminar(id);
    }

}
