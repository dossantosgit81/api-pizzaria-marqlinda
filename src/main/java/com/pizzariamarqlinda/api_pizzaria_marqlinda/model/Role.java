package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "roles")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
