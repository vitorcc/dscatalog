package com.imc.dscatalog.tests;

import com.imc.dscatalog.dto.CategoryDTO;
import com.imc.dscatalog.dto.ProductDTO;
import com.imc.dscatalog.entities.Category;
import com.imc.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(
                1L,
                "Phone",
                "Test phone",
                500.00,
                "https://test.com.br",
                Instant.parse("2018-11-30T18:35:24.00Z")
        );
        product.getCategories().add(createCategory());
        return product;
    }
    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
    public static Category createCategory(){
        return new Category(
                1L,
                "Test New Category"
        );
    }
    public static CategoryDTO createCategoryDTO(){
        Category category = createCategory();
        return new CategoryDTO(category);
    }

}
