package com.luv2code.ecommerce.service;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.ecommerce.dao.OrderRepository;
import com.luv2code.ecommerce.dto.Refund;
import com.luv2code.ecommerce.dto.RefundResponse;
import com.luv2code.ecommerce.entity.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;

@Service
public class RefundService {

    private static final String KEY = "rzp_test_8gUymVZqeZ4DM5";
    private static final String SECRET = "sHM71fTcTJ6vosNZzHE9VXBi";

    @Autowired
    OrderRepository orderRepository;

    public RefundResponse processRefund(Refund refund) {

        Order order = orderRepository.findByOrderTrackingNumber(refund.getOrderId());
        RefundResponse responseToReturn = new RefundResponse();
        try{
            RazorpayClient razorpay = new RazorpayClient(KEY, SECRET);
            String paymentId = order.getPaymentId();
            System.out.println("paymentId="+paymentId);

            // only the last order with the total Amount as 14 and the new payments will work since before those no orders have the paymentId

            JSONObject refundRequest = new JSONObject();
            // System.out.println("amount="+order.getTotalPrice().multiply(new BigDecimal(100)));
            refundRequest.put("amount", order.getTotalPrice().toBigInteger().intValue());
            refundRequest.put("speed","normal");
            // JSONObject notes = new JSONObject();
            // notes.put("notes_key_1","Tea, Earl Grey, Hot");
            // notes.put("notes_key_2","Tea, Earl Grey… decaf.");
            // refundRequest.put("notes",notes);
            // refundRequest.put("receipt","Receipt No. #31");
                        
            com.razorpay.Refund refundResponse = razorpay.payments.refund(paymentId,refundRequest);
            System.out.println(refundResponse);
            responseToReturn.setAmount(refundResponse.get("amount"));
            responseToReturn.setId(refundResponse.get("id"));
            responseToReturn.setSpeed("normal");

            // return responseToReturn;
        } catch (Exception e) {
            System.out.println("Error in creating Razorpay client" + e);
        }
        return responseToReturn;
    }
    
}
