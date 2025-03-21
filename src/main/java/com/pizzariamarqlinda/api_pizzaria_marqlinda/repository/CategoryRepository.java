package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
