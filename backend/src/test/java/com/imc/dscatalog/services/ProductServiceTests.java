package com.imc.dscatalog.services;

import com.imc.dscatalog.dto.ProductDTO;
import com.imc.dscatalog.entities.Category;
import com.imc.dscatalog.entities.Product;
import com.imc.dscatalog.repositories.CategoryRepository;
import com.imc.dscatalog.repositories.ProductRepository;
import com.imc.dscatalog.services.exceptions.DatabaseException;
import com.imc.dscatalog.services.exceptions.ResourceNotFoundException;
import com.imc.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private Category category;
    private ProductDTO productDTO;
    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 999L;
        dependentId = 3L;
        product = Factory.createProduct();
        category = Factory.createCategory();
        page = new PageImpl<>(List.of(product));
        productDTO = Factory.createProductDTO();
        /*
        Quando temos um método que é void, primeiro temos a ação para depois termos o "When"
        porém no caso de o método não ser void, inverte e primeiro vem o when e dps a ação:
         */
        when(productRepository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
        when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(productRepository.getReferenceById(existingId)).thenReturn(product);
        when(productRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        doNothing().when(productRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
        doThrow(DatabaseException.class).when(productRepository).deleteById(dependentId);
    }
    @Test
    void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingId);
        });
        verify(productRepository, times(1)).deleteById(existingId);
    }
    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           productService.delete(nonExistingId);
        });
        verify(productRepository, times(1)).deleteById(nonExistingId);
    }
    @Test
    void deleteShouldThrowDatabaseExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(DatabaseException.class, () -> {
            productService.delete(dependentId);
        });
        verify(productRepository, times(1)).deleteById(dependentId);
    }
    @Test
    void findAllPagedShouldReturnPage(){
        Pageable page = PageRequest.of(0, 10);
        Page<ProductDTO> result = productService.findAllPaged(page);
        Assertions.assertNotNull(result);
        verify(productRepository).findAll(page);
    }
    @Test
    void findByIdShouldReturnProductDTOWhenIdExists(){
        ProductDTO result = productService.findById(existingId);
        Assertions.assertNotNull(result);
    }
    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(nonExistingId);
        });
    }
    @Test
    void updateShouldReturnProductDTOWhenIdExists(){
        ProductDTO result = productService.update(existingId, productDTO);
        Assertions.assertNotNull(result);
    }
    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(nonExistingId, productDTO);
        });
    }
}
