package com.imc.dscatalog.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imc.dscatalog.dto.ProductDTO;
import com.imc.dscatalog.services.ProductService;
import com.imc.dscatalog.services.exceptions.DatabaseException;
import com.imc.dscatalog.services.exceptions.ResourceNotFoundException;
import com.imc.dscatalog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService service;
    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;
    private long existingId;
    private long nonExistingId;
    private long dependentId;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 999L;
        dependentId = 50L;
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));
        when(service.findAllPaged(any())).thenReturn(page);
        when(service.findById(existingId)).thenReturn(productDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        when(service.update(eq(existingId), any())).thenReturn(productDTO);
        when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);
        when(service.create(any())).thenReturn(productDTO);
    }
    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result =
                mockMvc.perform(delete("/products/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNoContent());
    }
    @Test
    void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result =
                mockMvc.perform(delete("/products/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }
    @Test
    void insertShouldReturnProductDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result =
                mockMvc.perform(post("/products")
                    .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }
    @Test
    void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result =
                mockMvc.perform(put("/products/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }
    @Test
    void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        ResultActions result =
                mockMvc.perform(put("/products/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }
    @Test
    void findByIdShouldReturnProductWhenIdExists() throws Exception{
        ResultActions result =
                mockMvc.perform(get("/products/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }
    @Test
    void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        ResultActions result =
                mockMvc.perform(get("/products/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isNotFound());
    }
    @Test
    void findAllShouldReturnPage() throws Exception {
        mockMvc.perform(get("/products")).andExpect(status().isOk());
        /*
         Também podemos fazer as assertions da seguinte forma:

        ResultActions result = mockMvc.perform(get("/products"));
        result.andExpect(status().isOk());
         */

        /*
        Também podemos fazer o teste aplicando o MediaType, para sabermos
        que tipo de resposta será aceito pela requisição:

        ResultActions result =
                mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON)
                );
        result.andExpect(status().isOk());

         */
    }
}
