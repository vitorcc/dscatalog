package com.imc.dscatalog.repositories;

import com.imc.dscatalog.entities.Product;
import com.imc.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    ProductRepository productRepository;
    private long existingId;
    private long nonExistingId;
    private long totalProducts;
    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 999L;
        totalProducts = 25L;
    }
    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        /*
        Padrão AAA
            Arrange: instancie os objetos necessários
            Act: execute as ações necessárias
            Assert: declare o que deveria acontecer (resultado esperado)
         */
        //long existingId = 1L; //Arrange
        productRepository.deleteById(existingId); //Act
        Optional<Product> result = productRepository.findById(existingId);
        Assertions.assertFalse(result.isPresent()); //assert
    }
    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenDoesNotExistsId(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
           productRepository.deleteById(nonExistingId);
        });
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);
        product = productRepository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(totalProducts + 1, product.getId());
    }

    @Test
    public void findShouldReturnNotEmptyWhenIdExists(){
        Optional<Product> result = productRepository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findShouldReturnEmptyWhenIdNotExists(){
        Optional<Product> result = productRepository.findById(nonExistingId);
        Assertions.assertTrue(result.isEmpty());
    }
}
