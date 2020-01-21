package com.example.beerlab.payload;

public class AddBeerToOrderPayload {

    private Long beerId;
    private int quantity;

    public AddBeerToOrderPayload(Long beerId, int quantity) {
        this.beerId = beerId;
        this.quantity = quantity;
    }

}
