package com.luv2code.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.ecommerce.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
}
