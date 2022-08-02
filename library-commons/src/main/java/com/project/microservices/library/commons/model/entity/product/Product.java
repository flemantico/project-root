package com.project.microservices.library.commons.model.entity.product;

import com.project.microservices.library.commons.constants.Status;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
//import javax.validation.constraints.Size;


@Entity
@Data
@Table(name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = -4002217729139480347L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //@Size(min = 4, max = 120, message = "The name must be between 4 and 120 characters.")
    @Column(name = "name", length = 120)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_at")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "expiration_on")
    private LocalDateTime expirationOn;

    @Column(name = "status")
    private Status status;
}
