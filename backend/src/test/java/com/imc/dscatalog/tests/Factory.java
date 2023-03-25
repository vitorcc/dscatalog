package com.imc.dscatalog.tests;

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
        product.getCategories().add(new Category(2L, "Test category"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
