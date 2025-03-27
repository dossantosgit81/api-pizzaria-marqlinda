package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemProduct> items;

    @Transient
    private BigDecimal total;

    public BigDecimal getTotal() {
        return items.stream().map(ItemProduct::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
