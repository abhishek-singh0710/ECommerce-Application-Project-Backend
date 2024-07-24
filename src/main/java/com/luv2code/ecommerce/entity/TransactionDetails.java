package com.luv2code.ecommerce.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    private String orderId;
    private String orderCurrency;
    private Integer amount;
    private String key;
    
    // public TransactionDetails(String orderId, String orderCurrency, Integer amount) {
    //     this.orderId = orderId;
    //     this.orderCurrency = orderCurrency;
    //     this.amount = amount;
    // }
}
