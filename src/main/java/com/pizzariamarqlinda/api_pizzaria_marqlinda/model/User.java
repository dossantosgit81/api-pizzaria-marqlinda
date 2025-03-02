package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    @Column(unique = true)
    private String email;

    @Column(length = 300)
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "users_roles",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Cart cart;
}
