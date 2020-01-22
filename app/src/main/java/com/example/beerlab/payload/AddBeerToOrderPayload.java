package com.example.beerlab.payload;

/**
 * Class that holds AddBeerToOrderPayload object which later is send to the backend
 */
public class AddBeerToOrderPayload {

    private Long beerId;
    private int quantity;

    public AddBeerToOrderPayload(Long beerId, int quantity) {
        this.beerId = beerId;
        this.quantity = quantity;
    }

}
