package com.luv2code.ecommerce.service;

import com.luv2code.ecommerce.dto.Purchase;
import com.luv2code.ecommerce.dto.PurchaseResponse;
import com.luv2code.ecommerce.entity.TransactionDetails;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

public interface CheckoutService {
    
    PurchaseResponse placeOrder(Purchase purchase);

    public TransactionDetails createTransaction(Integer amount);

    public TransactionDetails prepareTransaction(Order order);
}
