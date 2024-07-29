package com.luv2code.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.ecommerce.dto.Refund;
import com.luv2code.ecommerce.dto.RefundResponse;
import com.luv2code.ecommerce.service.RefundService;

@RestController
@RequestMapping("/api/payment")
public class RefundController {

    @Autowired
    RefundService refundService;
    
    @CrossOrigin(origins = "https://localhost:4200")
    @PostMapping("/refund")
    public RefundResponse refund(@RequestBody Refund refund) {
        // System.out.println(refund.getEmail());
        // System.out.println(refund.getOrderId());
        return this.refundService.processRefund(refund);
    }
}
