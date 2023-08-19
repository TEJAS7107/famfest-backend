package com.example.demo.services;

import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderFormat;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentGatewayService {

    private String EventName;

    private Long EventPrice;

    private String UserId;

    private static final String SECRET_ID = "rzp_test_GhTFog6DkVqSHO";

    private static final String SECRET_KEY = "cCdOc4tXtBoT7q8T1hHRlnls";

    private static final String CURRENCY = "INR";

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public Long getEventPrice() {
        return EventPrice;
    }

    public void setEventPrice(Long eventPrice) {
        EventPrice = eventPrice;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public PaymentGatewayService() {
        // Required for @Service to Implement pls do not remove
    }

    public PaymentGatewayService(String eventName, Long price, String userId) {
        this.EventName = eventName;
        this.EventPrice = price;
        this.UserId = userId;

        // System.out.println(EventName);
        // System.out.println(EventPrice);
        // System.out.println(userId);
    }

    public OrderFormat createTransaction(Long amount) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100));
            jsonObject.put("currency", CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(SECRET_ID, SECRET_KEY);

            Order order = razorpayClient.orders.create(jsonObject);
            System.out.println(EventPrice);

            OrderFormat transactionDeatilsFormat = prepareTransaction(order);

            return transactionDeatilsFormat;
        } catch (RazorpayException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    public OrderFormat prepareTransaction(Order order) {
        Integer amount = order.get("amount");
        String id = order.get("id");
        String currency = order.get("currency");
        String key = SECRET_ID;

        OrderFormat orderFormat = new OrderFormat(id, currency, amount, key);

        return orderFormat;
    }

}
