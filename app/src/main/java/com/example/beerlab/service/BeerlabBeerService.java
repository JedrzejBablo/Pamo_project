package com.example.beerlab.service;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.R;
import com.example.beerlab.adapter.BeerListAdapter;
import com.example.beerlab.api.BeerlabBeerApi;
import com.example.beerlab.api.BeerlabOrderApi;
import com.example.beerlab.model.Beer;
import com.example.beerlab.payload.AddBeerToOrderPayload;
import com.example.beerlab.view.MenuFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BeerlabBeerService {

    private View view;
    private RecyclerView recyclerView;
    private BeerlabAuthService beerlabAuthService;
    private MenuFragment menuFragment;
    private Activity activity;


    public BeerlabBeerService(View view, RecyclerView recyclerView, MenuFragment menuFragment, Activity activity, Context context) {
        this.view = view;
        this.recyclerView = recyclerView;
        this.menuFragment = menuFragment;
        this.activity = activity;
        this.beerlabAuthService = new BeerlabAuthService(context);
    }

    public void showBeers(){

        final Retrofit askBeers = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeerlabBeerApi beerService = askBeers.create(BeerlabBeerApi.class);

        Call<List<Beer>> callBeers = beerService.getBeers(beerlabAuthService.getToken());

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
    }

    private void showData(List<Beer> beers, View view){

        recyclerView = view.findViewById(R.id.recycler_view);
        final BeerListAdapter beerListAdapter = new BeerListAdapter(menuFragment, beers);

        beerListAdapter.setOnItemClickListener(new BeerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                AddBeerToOrderPayload addBeerToOrderPayload = new AddBeerToOrderPayload(beerListAdapter.getBeer(position).getId(),1);
                addBeerToCart(addBeerToOrderPayload);

            }
        });

        recyclerView.setAdapter(beerListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

    }

    private void addBeerToCart(AddBeerToOrderPayload addBeerToOrderPayload){
        final Retrofit askBeers = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeerlabOrderApi orderService = askBeers.create(BeerlabOrderApi.class);

        Call<AddBeerToOrderPayload> addBeerToCart = orderService.addBeerToCart(beerlabAuthService.getToken(),addBeerToOrderPayload);

        addBeerToCart.enqueue(new Callback<AddBeerToOrderPayload>() {
            @Override
            public void onResponse(Call<AddBeerToOrderPayload> call, Response<AddBeerToOrderPayload> response) {
                if(!response.isSuccessful()){
                    System.out.println("Something went wrong in adding to cart " + response.code());
                    return;
                }

            }

            @Override
            public void onFailure(Call<AddBeerToOrderPayload> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
    }
}
