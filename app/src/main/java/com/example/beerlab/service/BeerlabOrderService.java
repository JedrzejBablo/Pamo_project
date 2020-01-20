package com.example.beerlab.service;

import com.example.beerlab.model.Order;
import com.example.beerlab.payload.AddBeerToOrderPayload;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BeerlabOrderService {

    @POST("/api/order")
    Call<AddBeerToOrderPayload> addBeerToCart(
            @Header("X-Auth-Token") String authorization,
            @Body AddBeerToOrderPayload addBeerToOrderPayload
    );

    @POST("/api/order/reduce/{id}")
    Call<AddBeerToOrderPayload> reduceOrderBeerQuantity(
            @Header("X-Auth-Token") String authorization,
            @Body AddBeerToOrderPayload addBeerToOrderPayload
    );

    @DELETE("/api/order/{orderId}/delete/{beerId}")
    Call<Order> deleteBeerFromOrder(
            @Header("X-Auth-Token") String authorization,
            @Path("orderId") Long orderId,
            @Path("beerId") Long beerId
    );

    @GET("/api/user/order")
    Call<Order> getOrder(
            @Header("X-Auth-Token") String authorization
    );




}
