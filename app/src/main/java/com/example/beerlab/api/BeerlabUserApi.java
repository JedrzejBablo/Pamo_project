package com.example.beerlab.api;

import com.example.beerlab.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * In BeerlabAuthApi interface we use Retrofit to make requests to independent API
 * This interface handle with user requests
 */
public interface BeerlabUserApi {

    @GET("/api/user/me")
    Call<User> checkMe(@Header("X-Auth-Token") String authorization);

}
