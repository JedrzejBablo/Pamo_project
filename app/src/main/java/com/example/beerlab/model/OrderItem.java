package com.example.beerlab.model;

/**
 * Class that holds OrderItem for requesting
 */
public class OrderItem {
    private long id;
    private Beer beerDto;
    private Double unitPrice;
    private Integer quantity;

    public OrderItem(Beer beerDto, Double unitPrice, Integer quantity) {
        this.beerDto = beerDto;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public Beer getBeerDto() {
        return beerDto;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
