package com.example.beerlab.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Order {

    private Long id;
    private List<OrderItem> orderItemsDto;
    private OrderStatus status;
    private LocalDateTime startedTime;
    private LocalDateTime completeTime;
    private Double totalPrice;

    public Order(List<OrderItem> orderItemsDto, OrderStatus status, LocalDateTime startedTime, LocalDateTime completeTime, Double totalPrice) {
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

    public LocalDateTime getStartedTime() {
        return startedTime;
    }

    public LocalDateTime getCompleteTime() {
        return completeTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}
