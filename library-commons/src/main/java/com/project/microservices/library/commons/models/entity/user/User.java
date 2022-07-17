package com.project.microservices.library.commons.models.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 4002221912401133094L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //@Size(min = 4, max = 10, message = "The username must be between 4 and 10 characters.")
    @Column(name = "username", unique = true, length = 20)
    private String username;

    @Column(name = "password", length = 60)
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    //@Size(min = 4, max = 15, message = "The name must be between 4 and 15 characters.")
    @Column(name = "name", length = 15, nullable = false)
    private String name;

    //@Size(min = 4, max = 15, message = "The last name must be between 4 and 15 characters.")
    @Column(name = "last_name", length = 15, nullable = false)
    private String lastName;

    //@Email
    @Column(name = "email", unique = true, length = 100, nullable = false)
    private String email;

    @Column(name = "telephone", length = 50)
    private String telephone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            //Restrición de clave:
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
    private List<Role> roles;

    private Integer intentos;


    /**
     * Set values pre persist.
     */
    @PrePersist
    public void prePersist(){
        enabled = true;
    }
}
