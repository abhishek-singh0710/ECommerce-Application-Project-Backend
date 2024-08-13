package com.luv2code.ecommerce.service;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.ecommerce.dao.CustomerRepository;
import com.luv2code.ecommerce.dao.InventoryRepository;
import com.luv2code.ecommerce.dao.ProductRepository;
import com.luv2code.ecommerce.dto.Purchase;
import com.luv2code.ecommerce.dto.PurchaseResponse;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.entity.Inventory;
import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.entity.OrderItem;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.TransactionDetails;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    private ProductRepository productRepository;

    private static final String KEY = "rzp_test_8gUymVZqeZ4DM5";
    private static final String SECRET = "sHM71fTcTJ6vosNZzHE9VXBi";
    private static final String CURRENCY = "INR";

    public CheckoutServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item)); // Using the add function which we created to add the items to the
                                                     // order

        for(OrderItem item: orderItems) {
            Product product = productRepository.findById(item.getProductId()).get();
            product.setUnitsInStock(product.getUnitsInStock() - item.getQuantity());
            productRepository.save(product);
        }

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        order.setPaymentId(purchase.getPaymentId());

        // populate customer with order
        Customer customer = purchase.getCustomer();

        // Check is the user already exists in the database
        String theEmail = customer.getEmail();
        Customer customerFromDB = customerRepository.findByEmail(theEmail);
        if (customerFromDB != null) {
            // We found the customer in the Database And so We can Assign Them Accordingly
            System.out.println("email=" + theEmail + "customer=" + customerFromDB.getFirstName());
            customer = customerFromDB;
        }

        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber, purchase.getPaymentId());
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number (UUID version-4)

        return UUID.randomUUID().toString();
    }

    @Override
    public TransactionDetails createTransaction(Integer amount) {
        // will require
        // amount Receiving as the parameter
        // currency "INR", "USD", etc.
        // key
        // secret key
        try {
            JSONObject object = new JSONObject();
            object.put("amount", (amount));  // Razorpay takes the smallest unit of currency so to convert it into Dollars Or Rupees ( * 100)
            object.put("currency", "USD");


            RazorpayClient razorpay = new RazorpayClient(KEY, SECRET);
            com.razorpay.Order order = razorpay.orders.create(object);   // Using direct import since we have another Order class also 
            TransactionDetails transactionDetail = prepareTransaction(order);
            System.out.println("transactionDetails="+transactionDetail);
            return transactionDetail;
            // System.out.println(order);
        } catch (Exception e) {
            System.out.println("Error in creating the Razorpay Client " + e);
        }
        return null;
    }

    @Override
    public TransactionDetails prepareTransaction(com.razorpay.Order order) {
        String orderId = order.get("id");
        String orderCurrency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId, orderCurrency, amount, KEY);
        return transactionDetails;
    }
}
