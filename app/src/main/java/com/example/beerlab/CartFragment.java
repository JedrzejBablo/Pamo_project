package com.example.beerlab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.adapter.BeerListAdapter;
import com.example.beerlab.adapter.OrderItemListAdapter;
import com.example.beerlab.model.Beer;
import com.example.beerlab.model.Order;
import com.example.beerlab.model.OrderItem;
import com.example.beerlab.model.User;
import com.example.beerlab.service.BeerlabBeerService;
import com.example.beerlab.service.BeerlabOrderService;
import com.example.beerlab.service.BeerlabUserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button addToCartButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart,container,false);

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
                if(!response.isSuccessful()){
                    System.out.println("Wooooow, something went wrong in capturing user credentials! :(" + response.code());
                    return;
                }
                User user = response.body();
                System.out.println(user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });



        final Retrofit askOrder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeerlabOrderService orderService = askOrder.create(BeerlabOrderService.class);

        Call<Order> callOrder = orderService.getOrder(token);

        callOrder.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (!response.isSuccessful()){
                    System.out.println("Wooooow, something went wrong ! :( " + response.code());
                    return;
                }

                showData(response.body().getOrderItemsDto(), view);

            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                System.out.println(t.getMessage());
            }


        });



        return view;
    }

    private void showData(List<OrderItem> orders, View view){

        mRecyclerView = view.findViewById(R.id.order_item_recycler_view);
        OrderItemListAdapter orderItemListAdapter = new OrderItemListAdapter(this, orders);
//        orderItemListAdapter.setOnItemClickListener(new OrderItemListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//            }
//        });
        mRecyclerView.setAdapter(orderItemListAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

//    private void setButton() {
//        addToCartButton = addToCartButton.findViewById(R.id.button_addToCart);
//        addToCartButton.setOnClickListener(onClickListener);
//    }

}
