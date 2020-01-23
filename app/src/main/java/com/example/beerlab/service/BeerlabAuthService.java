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

/**
 * Service class which is responsible for authentication with backend.
 * In BeerlabAuthService we store methods which are sending API calls to backend
 *  to authenticate with server.
 */
public class BeerlabAuthService {

    Context context;

    public BeerlabAuthService(Context context){
        this.context = context;
    }

    /**
     * getToken function is responsible for getting token
     * @return
     */
    public String getToken(){
        MyApplication app = (MyApplication) context;

        SharedPreferences sharedPreferences = app.getSharedPrefs();
        return sharedPreferences.getString("token", "");
    }

    public void flushToken() {
        MyApplication app = (MyApplication) context;

        SharedPreferences.Editor editor = app.getSharedPrefs().edit();
        editor.clear().apply();
    }

    /**
     * verifyUser function is responsible for authenticate user with server
     * @param baseUrl
     */
    public void verifyUser(String baseUrl) {
        final Retrofit askUser = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
