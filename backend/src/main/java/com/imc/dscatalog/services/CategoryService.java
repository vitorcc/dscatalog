package com.imc.dscatalog.services;

import com.imc.dscatalog.dto.CategoryDTO;
import com.imc.dscatalog.entities.Category;
import com.imc.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/*
    @Service -> REGISTRA A CLASSE COMO COMPONENTE QUE IRÁ PARTICIPAR
    DO SISTEMA DE INJEÇÃO DE DEPEDÊNCIA AUTOMATIZADO DO SPRING
    QUEM IRÁ GERENCIAR AS INSTÂNCIAS DAS DEPENDÊNCIAS DOS OBJETOS
    TIPO CATEGORYSERVICE SERÁ O SPRINGBOOT
    PARA REGISTRAR UM COMPONENTE PODEMOS UTILIZAR:
    @COMPONENT -> QUANDO É UM COMPONENTE GENÉRICO SEM UM SIGNIFICADO ESPECÍFICO
    @REPOSITORY
    @SERVICE
    ETC...
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /*
        @Transactional iremos garantir que ou tudo seja executado, ou nada.
        O readOnly serve
        para evitar locking no banco, ou seja, não iremos travar o
        banco só pra ler, é uma forma de melhorar a peformance.
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

}
