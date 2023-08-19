package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
//import org.springframework.data.mongodb.core.mapping.id;
import org.springframework.data.annotation.Id;

@Document(collection = "TransactionDetails")
public class TransactionDetails {

    @Field
    private String userId;

    @Field
    private Long amount;

    @Field
    private Long tickets;

    @Id
    private String razorpay_payment_id;

    @Field
    private String razorpay_order_id;

    @Field
    private String eventId;

    @Field
    private String eventName;

    @Field
    private String razorpay_signature;

    @Field
    private String timeStamp;

    public TransactionDetails() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getTickets() {
        return tickets;
    }

    public void setTickets(Long tickets) {
        this.tickets = tickets;
    }

    public String getRazorpay_payment_id() {
        return razorpay_payment_id;
    }

    public void setRazorpay_payment_id(String razorpay_payment_id) {
        this.razorpay_payment_id = razorpay_payment_id;
    }

    public String getRazorpay_order_id() {
        return razorpay_order_id;
    }

    public void setRazorpay_order_id(String razorpay_order_id) {
        this.razorpay_order_id = razorpay_order_id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getRazorpay_signature() {
        return razorpay_signature;
    }

    public void setRazorpay_signature(String razorpay_signature) {
        this.razorpay_signature = razorpay_signature;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
