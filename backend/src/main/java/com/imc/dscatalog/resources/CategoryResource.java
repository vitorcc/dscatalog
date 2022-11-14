package com.imc.dscatalog.resources;

import com.imc.dscatalog.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "categories")
public class CategoryResource {

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(2L, "Books"));
        categories.add(new Category(6L, "Notebooks"));
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
