package com.imc.dscatalog.services;

import com.imc.dscatalog.entities.Category;
import com.imc.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

}
