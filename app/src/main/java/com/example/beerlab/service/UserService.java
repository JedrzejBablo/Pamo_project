package com.example.beerlab.service;

import com.example.beerlab.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserService {
    @GET("/api/user/me")
    Call<User> checkMe(@Header("X-Auth-Token") String authorization);
}
