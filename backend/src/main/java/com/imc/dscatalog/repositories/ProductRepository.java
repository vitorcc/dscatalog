package com.imc.dscatalog.repositories;

import com.imc.dscatalog.entities.Category;
import com.imc.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
