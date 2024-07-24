package com.luv2code.ecommerce.dao;

import com.luv2code.ecommerce.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);
    // Now Spring Data Rest will automatically expose the endpoint /api/products/search/findByCategoryId?id=2


    Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);
    // Now Spring Data REST Will Automatically Expose The Endpoint /api/products/search/findByNameContaining?name=Java,Python,etc
}
