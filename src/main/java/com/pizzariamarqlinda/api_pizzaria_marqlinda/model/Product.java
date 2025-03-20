package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String details;

    private BigDecimal price;

    private Boolean highlight;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "IMG_URL")
    private String imgUrl;
}
