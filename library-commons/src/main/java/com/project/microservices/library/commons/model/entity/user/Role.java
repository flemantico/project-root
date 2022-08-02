package com.project.microservices.library.commons.model.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "roles")
public class Role implements Serializable {
    private static final long serialVersionUID = 4467531611801172710L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, length = 15)
    //@Size(min = 5, max = 15, message = "The name must be between 4 and 15 characters.")
    private String name;

    /*
    Para implementar selección inversa
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private List<User> users;
    */

}
