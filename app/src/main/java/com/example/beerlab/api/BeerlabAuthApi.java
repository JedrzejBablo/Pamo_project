package com.example.beerlab.api;

import com.example.beerlab.payload.LoginPayload;
import com.example.beerlab.payload.RegisterPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BeerlabAuthApi {
    @POST("/api/auth/signin")
    Call<LoginPayload> login(@Body LoginPayload loginPayload);

    @POST("/api/auth/signup")
    Call<RegisterPayload> register(@Body RegisterPayload registerPayload);
}
