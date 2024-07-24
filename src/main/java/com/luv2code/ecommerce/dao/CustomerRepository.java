package com.luv2code.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.ecommerce.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Select * FROM Customer WHERE c.email = theEmail
    Customer findByEmail(String theEmail);
}
