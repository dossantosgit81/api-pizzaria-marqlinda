package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
