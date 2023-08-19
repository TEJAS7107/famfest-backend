package com.example.demo.model;

public class OrderFormat {

    private String OrderId;

    private String Currency;

    private Integer amount;

    private String key;

    public OrderFormat(String orderId, String currency, Integer amount, String key) {
        OrderId = orderId;
        Currency = currency;
        this.amount = amount;
        this.key = key;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
