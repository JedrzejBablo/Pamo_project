package com.example.beerlab.api;

import com.example.beerlab.payload.LoginPayload;
import com.example.beerlab.payload.RegisterPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * In BeerlabAuthApi interface we use Retrofit to make requests to independent API
 * This interface handle with auth endpoint authentication requests
 */
public interface BeerlabAuthApi {
    @POST("/api/auth/signin")
    Call<LoginPayload> login(@Body LoginPayload loginPayload);

    @POST("/api/auth/signup")
    Call<RegisterPayload> register(@Body RegisterPayload registerPayload);
}
