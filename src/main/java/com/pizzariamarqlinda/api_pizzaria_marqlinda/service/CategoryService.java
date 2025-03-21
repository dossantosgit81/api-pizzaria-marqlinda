package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.CategoryMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Category;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CategoryReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CategoryResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper = CategoryMapper.INSTANCE;

    public Long save(CategoryReqDto categoryReqDto){
        Category category = new Category();
        category.setName(categoryReqDto.name());
        return repository.save(category).getId();
    }

    public void update(Long id, CategoryReqDto categoryReqDto){
        Optional<Category> categorySearched = repository.findById(id);
        if(categorySearched.isPresent()){
           Category category = categorySearched.get();
           category.setName(categoryReqDto.name());
           repository.save(category);
        }else
            throw new ObjectNotFoundException("Categoria não encontrada.");
    }

    public List<CategoryResDto> all(){
        List<Category> categories = repository.findAll();
        return categories.stream().map(mapper::categoryResDto).toList();
    }

    public Category findById(Long idCategory){
        return repository.findById(idCategory).orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada."));
    }

    public void delete(Long id){
        var category = findById(id);
        repository.delete(category);
    }

}
