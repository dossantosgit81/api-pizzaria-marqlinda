package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    private Long id;
}
