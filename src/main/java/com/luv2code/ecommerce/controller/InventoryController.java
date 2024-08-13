package com.luv2code.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.ecommerce.entity.OrderItem;
import com.luv2code.ecommerce.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;
    
    @PostMapping("/getInventory") 
    public boolean checkInInventory(@RequestBody OrderItem[] orderItems) {
        return inventoryService.checkInInventory(orderItems);
    }
}
