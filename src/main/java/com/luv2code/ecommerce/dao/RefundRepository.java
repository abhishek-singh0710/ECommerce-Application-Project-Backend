package com.luv2code.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.luv2code.ecommerce.entity.Refund;

@RepositoryRestResource
public interface RefundRepository extends JpaRepository<Refund, Long> {
    
}
