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
import com.example.beerlab.fragment.MenuFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * BeerlabBeerService is a service class that is responsible for managing beers.
 */
public class BeerlabBeerService {

    private View view;
    private RecyclerView recyclerView;
    private BeerlabAuthService beerlabAuthService;
    private MenuFragment menuFragment;
    private Activity activity;
    private String baseUrl;


    public BeerlabBeerService(View view, MenuFragment menuFragment, Activity activity, Context context, String baseUrl) {
        this.view = view;
        this.menuFragment = menuFragment;
        this.activity = activity;
        this.beerlabAuthService = new BeerlabAuthService(context);
        this.baseUrl = baseUrl;
    }
/**
 * showBeers function is responsible receive Beer list from API and then call showData function to show them in MenuFragment.
 * It use Retrofit library to receive json with list of beers and parse it into Beer objects list
 * @param baseUrl
 */
    public void showBeers(String baseUrl){

        final Retrofit askBeers = new Retrofit.Builder()
                .baseUrl(baseUrl)
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

    /**
     * showData function is responsible for show beerList in MenuFragment.
     * It use BeerListAdapter class to put appropriate data received from server to
     * recycler view. It also implement onItemClick method from BeerlistAdapter.OnItemClickListener interface.
     * @param beers
     * @param view
     */
    private void showData(List<Beer> beers, View view){

        recyclerView = view.findViewById(R.id.recycler_view);
        final BeerListAdapter beerListAdapter = new BeerListAdapter(menuFragment, beers);

        beerListAdapter.setOnItemClickListener(new BeerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                AddBeerToOrderPayload addBeerToOrderPayload = new AddBeerToOrderPayload(beerListAdapter.getBeer(position).getId(),1);
                addBeerToCart(addBeerToOrderPayload,baseUrl);

            }
        });

        recyclerView.setAdapter(beerListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

    }

    /**
     * AddBeerToCart method is responsible for obviously adding beer to cart. In that purpose it use
     * Retrofit library to post AddBeerOrderPayload to server.
     * @param addBeerToOrderPayload
     * @param baseUrl
     */
    private void addBeerToCart(AddBeerToOrderPayload addBeerToOrderPayload, String baseUrl){
        final Retrofit askBeers = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
                Snackbar.make(view, String.format("Successfully added item"), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddBeerToOrderPayload> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
    }
}
