package com.project.microservices.library.commons.model.entity.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.microservices.library.commons.model.entity.product.Product;
import lombok.Data;

import java.io.Serializable;

/***
 * It's not an entity class, just use Product.
 */
@Data
public class Item implements Serializable{
    private static final long serialVersionUID = 4225159998616601686L;

    //TODO
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private Product product;
    private Integer quantity;

    public Item() {
    }

    public Item(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

//    @JsonIgnore
//    public Double getTotal() {
//        return product.getPrice() * quantity.doubleValue();
//    }

}
