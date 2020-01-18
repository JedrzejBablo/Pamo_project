package com.example.beerlab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.beerlab.model.Beer;
import com.example.beerlab.model.User;
import com.example.beerlab.service.BeerlabBeerService;
import com.example.beerlab.service.BeerlabUserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends Fragment {

    private RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_menu,container,false);

        MyApplication app = (MyApplication) getContext().getApplicationContext();

        SharedPreferences sharedPreferences = app.getSharedPrefs();
        String token = sharedPreferences.getString("token", "");

        final Retrofit askUser = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeerlabUserService userService = askUser.create(BeerlabUserService.class);
        Call<User> call = userService.checkMe(token);
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



        final Retrofit askBeers = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        BeerlabBeerService beerService = askBeers.create(BeerlabBeerService.class);

        Call<List<Beer>> callBeers = beerService.getBeers(token);

        callBeers.enqueue(new Callback<List<Beer>>() {
            @Override
            public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {

                if (!response.isSuccessful()){
                    System.out.println("Wooooow, something went wrong ! :(" + response.code());
                    return;
                }
                showData(response.body(), view);

            }

            @Override
            public void onFailure(Call<List<Beer>> call, Throwable t) {
                    System.out.println(t.getMessage());
            }


        });



        return view;

    }

    private void showData(List<Beer> beers, View view){

        mRecyclerView = view.findViewById(R.id.recycler_view);
        BeerListAdapter beerListAdapter = new BeerListAdapter(this, beers);
        mRecyclerView.setAdapter(beerListAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


}
