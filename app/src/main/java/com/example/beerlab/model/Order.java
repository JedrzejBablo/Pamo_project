package com.example.beerlab.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that holds Order for requesting
 */
public class Order {

    private Long id;
    private List<OrderItem> orderItemsDto;
    private OrderStatus status;
    private Object startedTime;
    private Object completeTime;
    private Double totalPrice;

    public Order(List<OrderItem> orderItemsDto, OrderStatus status, Object startedTime, Object completeTime, Double totalPrice) {
        this.orderItemsDto = orderItemsDto;
        this.status = status;
        this.startedTime = startedTime;
        this.completeTime = completeTime;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public List<OrderItem> getOrderItemsDto() {
        return orderItemsDto;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Object getStartedTime() {
        return startedTime;
    }

    public Object getCompleteTime() {
        return completeTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}
