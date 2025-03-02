package com.pizzariamarqlinda.api_pizzaria_marqlinda.model;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.LoginReqDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serial;
import java.io.Serializable;
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
           name = "users_roles",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public boolean isValidUser(LoginReqDto loginReqDto, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(loginReqDto.password(), this.password);
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;
}
