package com.imc.dscatalog.resources;

import com.imc.dscatalog.entities.Category;
import com.imc.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){

        List<Category> categories = categoryService.findAll();
        /*
            Para instanciar o método ResponseEntity, usamos alguns
            métodos builder dele:
            - ResponseEntity.ok() -> Retorna uma response 200
            - .body(categories) -> Retorna o corpo da resposta
            - o corpo poderia ser retornado dentro do ok(categories)
         */
        return ResponseEntity.ok().body(categories);
    }
}
