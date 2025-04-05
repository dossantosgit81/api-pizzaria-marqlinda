package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "CONFIGURATIONS")
@Builder
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    @Type(ListArrayType.class)
    @Column(name = "values", columnDefinition = "varchar[]")
    private List<String> values;
}
