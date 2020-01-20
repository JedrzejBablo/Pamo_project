package com.example.beerlab.payload;

public class AddBeerToOrderPayload {

    private Long beerId;
    private int quantity;

    public AddBeerToOrderPayload(Long beerId, int quantity) {
        this.beerId = beerId;
        this.quantity = quantity;
    }

    public void setBeerId(Long beerId) {
        this.beerId = beerId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getBeerId() {
        return beerId;
    }

    public int getQuantity() {
        return quantity;
    }
}
