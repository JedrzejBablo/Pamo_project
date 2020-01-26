package com.example.beerlab.api;

import com.example.beerlab.model.Beer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * In BeerlabAuthApi interface we use Retrofit to make requests to independent API
 * This interface handle with beer endpoint requests
 */

public interface BeerlabBeerApi {

    @GET("/api/beer")
    Call<List<Beer>> getBeers(@Header("X-Auth-Token") String authorization);

    @GET("/api/beer/{id}")
    Call<Beer> getBeerById(
            @Header("X-Auth-Token") String authorization,
            @Path("id") Long id
    );
}


