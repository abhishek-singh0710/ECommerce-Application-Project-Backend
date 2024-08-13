package com.luv2code.ecommerce.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.luv2code.ecommerce.dao.InventoryRepository;
import com.luv2code.ecommerce.dto.Purchase;
import com.luv2code.ecommerce.entity.OrderItem;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public boolean checkInInventory(OrderItem[] orderItems) {
        for(OrderItem item: orderItems) {
            if(inventoryRepository.findById(item.getProductId()).get().getUnitsInStock() < item.getQuantity()) {
                return false;
            }
        }
        return true;
    }
    
}
