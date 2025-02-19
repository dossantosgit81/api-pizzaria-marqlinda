package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String phone;
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Address> addresses;
}
