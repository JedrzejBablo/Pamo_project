package com.example.beerlab.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.beerlab.utils.MyApplication;
import com.example.beerlab.api.BeerlabUserApi;
import com.example.beerlab.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BeerlabAuthService {

    Context context;

    public BeerlabAuthService(Context context){
        this.context = context;
    }

    public String getToken(){
        MyApplication app = (MyApplication) context;

        SharedPreferences sharedPreferences = app.getSharedPrefs();
        return sharedPreferences.getString("token", "");
    }

    public void verifyUser() {
        final Retrofit askUser = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeerlabUserApi userService = askUser.create(BeerlabUserApi.class);
        Call<User> call = userService.checkMe(getToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                System.out.println(user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
