package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
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

    @Enumerated(EnumType.STRING)
    private ProfilesUserEnum name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


}
