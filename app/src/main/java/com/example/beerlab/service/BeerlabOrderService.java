package com.example.beerlab.service;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.R;
import com.example.beerlab.adapter.BeerListAdapter;
import com.example.beerlab.adapter.OrderItemListAdapter;
import com.example.beerlab.api.BeerlabOrderApi;
import com.example.beerlab.model.Order;
import com.example.beerlab.model.OrderItem;
import com.example.beerlab.payload.AddBeerToOrderPayload;
import com.example.beerlab.view.CartFragment;
import com.example.beerlab.view.MenuFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BeerlabOrderService {

    private View view;
    private RecyclerView recyclerView;
    private Order order;
    private BeerlabAuthService beerlabAuthService;
    private CartFragment cartFragment;
    private Activity activity;


    public BeerlabOrderService(View view, RecyclerView recyclerView, CartFragment cartFragment, Activity activity, Context context) {
        this.view = view;
        this.recyclerView = recyclerView;
        this.cartFragment = cartFragment;
        this.activity = activity;
        this.beerlabAuthService = new BeerlabAuthService(context);
    }


    public void showCartItems(){
        final Retrofit askOrder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeerlabOrderApi orderService = askOrder.create(BeerlabOrderApi.class);

        Call<Order> callOrder = orderService.getOrder(beerlabAuthService.getToken());

        callOrder.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (!response.isSuccessful()){
                    System.out.println("Wooooow, something went wrong ! :( " + response.code());
                    return;
                }
                setOrder(response.body());
                showData(response.body().getOrderItemsDto(), view);

            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                System.out.println(t.getMessage());
            }


        });

    }

    private void setOrder(Order order){
        this.order = order;
    }


    private void showData(List<OrderItem> orders, View view){

        recyclerView = view.findViewById(R.id.order_item_recycler_view);
        final OrderItemListAdapter orderItemListAdapter = new OrderItemListAdapter(cartFragment, orders);

        orderItemListAdapter.setOnItemClickListener(new OrderItemListAdapter.OnItemClickListener() {
            @Override
            public void increaseQuantity(int position) {
                AddBeerToOrderPayload addBeerToOrderPayload = new AddBeerToOrderPayload(orderItemListAdapter.getOrderItem(position).getBeerDto().getId(),1);
                addBeerToCart(addBeerToOrderPayload);
            }

            @Override
            public void decreaseQuantity(int position) {
                AddBeerToOrderPayload addBeerToOrderPayload = new AddBeerToOrderPayload(orderItemListAdapter.getOrderItem(position).getBeerDto().getId(), 1);
                BeerlabOrderService.this.decreaseQuantity(order.getId(),addBeerToOrderPayload);

            }

            @Override
            public void deleteFromCart(int position) {
                deleteItemFromCart(order.getId(),orderItemListAdapter.getOrderItem(position).getBeerDto().getId());
            }
        });

        recyclerView.setAdapter(orderItemListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

    }

    private void decreaseQuantity(Long id,AddBeerToOrderPayload addBeerToOrderPayload){
        final Retrofit askBeers = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeerlabOrderApi orderService = askBeers.create(BeerlabOrderApi.class);

        Call<AddBeerToOrderPayload> decreaseBeerQuantity = orderService.reduceOrderBeerQuantity(beerlabAuthService.getToken(),id,addBeerToOrderPayload);

        decreaseBeerQuantity.enqueue(new Callback<AddBeerToOrderPayload>() {
            @Override
            public void onResponse(Call<AddBeerToOrderPayload> call, Response<AddBeerToOrderPayload> response) {
                if(!response.isSuccessful()){
                    System.out.println("Something went wrong in decreasing quantity " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<AddBeerToOrderPayload> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
    }

    private void deleteItemFromCart(Long orderId,Long beerId){
        final Retrofit askBeers = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeerlabOrderApi orderService = askBeers.create(BeerlabOrderApi.class);

        Call<Order> deleteItemFromCart = orderService.deleteBeerFromOrder(beerlabAuthService.getToken(),orderId,beerId);

        deleteItemFromCart.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(!response.isSuccessful()){
                    System.out.println("Something went wrong in deleting from cart " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
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
