package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "ITEMS_PRODUCTS")
@Builder
public class ItemProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    public BigDecimal getSubtotal(){
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    public void setSubtotal() {
        this.subtotal = getSubtotal();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = Objects.requireNonNullElse(quantity, 1);
    }
}
