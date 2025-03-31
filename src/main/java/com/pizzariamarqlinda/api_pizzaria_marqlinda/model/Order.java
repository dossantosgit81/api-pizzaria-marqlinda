package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "DATE_TIME_ORDER")
    private LocalDateTime dateTimeOrder;

    private BigDecimal total;

    private String observations;

    private Boolean delivery;

    @Column(name = "DELIVERY_FORECAST")
    private LocalDateTime deliveryForecast;

    @Column(name = "RATE_DELIVERY")
    private BigDecimal rateDelivery;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "DELIVERY_MAN_USER_ID")
    private User deliveryMan;

    @ManyToOne
    @JoinColumn(name = "ATTENDANT_USER_ID")
    private User attendant;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_METHOD_ID")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ItemProduct> items;

    public BigDecimal getTotal() {
        total = total.setScale(2, RoundingMode.HALF_UP);
        return total;
    }
}
