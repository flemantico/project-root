package com.project.microservices.app.service.item.service;//package com.project.springboot.app.item.service;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import com.project.springboot.app.commons.models.entity.product.Product;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.project.springboot.app.commons.models.entity.item.Item;
//
///***
// * This is an old way of communication between microservices.
// */
//@Service("ItemServiceRestTemplate")
//public class ItemServiceImpl implements IitemService {
//
//    @Autowired
//    private RestTemplate clientRest;
//
//    @Override
//    public List<Item> findAll() {
//        List<Product> products = Arrays.asList(clientRest.getForObject("http://service-product/listar", Product[].class));
//
//        return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
//    }
//
//    @Override
//    public Item findById(Long id, Integer quantity) {
//        Map<String, String> pathVariables = new HashMap<String, String>();
//        pathVariables.put("id", id.toString());
//        Product product = clientRest.getForObject("http://service-product/{id}", Product.class, pathVariables);
//        return new Item(product, quantity);
//    }
//
//    @Override
//    public Product save(Product product) {
//        HttpEntity<Product> request = new HttpEntity<Product>(product);
//        ResponseEntity<Product> response = clientRest.exchange("http://service-product/crear", HttpMethod.POST, request, Product.class);
//        Product productResponse = response.getBody();
//        return productResponse;
//    }
//
//    @Override
//    public Product update(Product product, Long id) {
//        Map<String, String> pathVariables = new HashMap<String, String>();
//        pathVariables.put("id", id.toString());
//
//        HttpEntity<Product> request = new HttpEntity<Product>(product);
//        ResponseEntity<Product> response = clientRest.exchange("http://service-product/editar/{id}", HttpMethod.PUT, request, Product.class, pathVariables);
//        Product productResponse = response.getBody();
//        return productResponse;
//    }
//
//    @Override
//    public void delete(Long id) {
//        Map<String, String> pathVariables = new HashMap<String, String>();
//        pathVariables.put("id", id.toString());
//
//        clientRest.delete("http://service-product/eliminar/{id}", pathVariables);
//    }
//}
