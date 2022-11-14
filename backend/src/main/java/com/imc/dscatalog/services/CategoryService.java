package com.imc.dscatalog.services;

import com.imc.dscatalog.dto.CategoryDTO;
import com.imc.dscatalog.entities.Category;
import com.imc.dscatalog.repositories.CategoryRepository;
import com.imc.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category category = obj.orElseThrow( () -> new EntityNotFoundException("Entidade não encontrada!"));
        return new CategoryDTO(category);
    }
}
