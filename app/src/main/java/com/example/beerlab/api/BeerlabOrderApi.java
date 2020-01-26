package com.example.beerlab.api;

import com.example.beerlab.model.Order;
import com.example.beerlab.model.OrderItem;
import com.example.beerlab.payload.AddBeerToOrderPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * In BeerlabAuthApi interface we use Retrofit to make requests to independent API
 * This interface handle with order endpoint authentication requests
 */
public interface BeerlabOrderApi {

    @POST("/api/order")
    Call<AddBeerToOrderPayload> addBeerToCart(
            @Header("X-Auth-Token") String authorization,
            @Body AddBeerToOrderPayload addBeerToOrderPayload
    );

    @POST("/api/order/reduce/{id}")
    Call<AddBeerToOrderPayload> reduceOrderBeerQuantity(
            @Header("X-Auth-Token") String authorization,
            @Path("id") Long itemId,
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

    @POST("/api/user/confirm/{method}")
    Call<OrderItem> confirmOrder(
            @Header("X-Auth-Token") String authorization,
            @Path("method") Long method
    );




}
