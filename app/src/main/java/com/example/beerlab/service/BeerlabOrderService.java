package com.example.beerlab.service;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.R;
import com.example.beerlab.adapter.OrderItemListAdapter;
import com.example.beerlab.api.BeerlabOrderApi;
import com.example.beerlab.model.Order;
import com.example.beerlab.model.OrderItem;
import com.example.beerlab.payload.AddBeerToOrderPayload;
import com.example.beerlab.fragment.CartFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * BeerlabOrderService is a class that is responsible for dealing with orders.
 */
public class BeerlabOrderService {

    private View view;
    private RecyclerView recyclerView;
    private Order order;
    private TextView totalAmountView;
    private BeerlabAuthService beerlabAuthService;
    private CartFragment cartFragment;
    private Activity activity;
    private String baseUrl;


    public BeerlabOrderService(View view, CartFragment cartFragment, Activity activity, Context context, String baseUrl) {
        this.view = view;
        this.cartFragment = cartFragment;
        this.activity = activity;
        this.beerlabAuthService = new BeerlabAuthService(context);
        this.baseUrl = baseUrl;
    }

    /**
     * Method that is responsible for setting total order value
     * @param view
     */
    public void setTotalAmountView(View view){
        totalAmountView = view.findViewById(R.id.textView_total);
        totalAmountView.setText(activity.getApplicationContext().getString(R.string.total_amount) + order.getTotalPrice());
    }

    /**
     * Method that is responsible for showing cart items. It is using Retrofit library to ask API
     * for current user order. When it get it, then showCartItems method is setting order, setting
     * total order value and call method showData
     * @param baseUrl
     */
    public void showCartItems(String baseUrl){
        final Retrofit askOrder = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
                setTotalAmountView(view);
                showData(response.body().getOrderItemsDto(), view);

            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                System.out.println(t.getMessage());
            }


        });

    }

    /**
     * confirmOrder method is responsible for calling API in order to confirm current user order
     * @param baseUrl
     */
    public void confirmOrder(String baseUrl){
        final Retrofit askOrder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeerlabOrderApi orderService = askOrder.create(BeerlabOrderApi.class);

        Call<OrderItem> callOrder = orderService.confirmOrder(beerlabAuthService.getToken(), 1L);

        callOrder.enqueue(new Callback<OrderItem>() {
            @Override
            public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                if (!response.isSuccessful()){
                    System.out.println("Wooooow, something went wrong ! :( " + response.code());
                    return;
                }

            }

            @Override
            public void onFailure(Call<OrderItem> call, Throwable t) {
                System.out.println(t.getMessage());
            }


        });
    }

    private void setOrder(Order order){
        this.order = order;
    }


    /**
     * showData method is responsible for showing current user order in application. It also implements
     * OrderItemListAdapter.OnItemClickListener interface.
     */
    private void showData(List<OrderItem> orders, final View view){

        recyclerView = view.findViewById(R.id.order_item_recycler_view);
        final OrderItemListAdapter orderItemListAdapter = new OrderItemListAdapter(cartFragment, orders);


        orderItemListAdapter.setOnItemClickListener(new OrderItemListAdapter.OnItemClickListener() {
            @Override
            public void increaseQuantity(int position) {
                AddBeerToOrderPayload addBeerToOrderPayload = new AddBeerToOrderPayload(orderItemListAdapter.getOrderItem(position).getBeerDto().getId(),1);
                addBeerToCart(addBeerToOrderPayload,baseUrl);
            }

            @Override
            public void decreaseQuantity(int position) {
                AddBeerToOrderPayload addBeerToOrderPayload = new AddBeerToOrderPayload(orderItemListAdapter.getOrderItem(position).getBeerDto().getId(), 1);
                BeerlabOrderService.this.decreaseQuantity(order.getId(),addBeerToOrderPayload,baseUrl);

            }

            @Override
            public void deleteFromCart(int position) {
                deleteItemFromCart(order.getId(),orderItemListAdapter.getOrderItem(position).getBeerDto().getId(),baseUrl);
            }
        });

        recyclerView.setAdapter(orderItemListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    /**
     * decreaseQuantity method is responsible for decreasing item quantity in cart
     * @param id
     * @param addBeerToOrderPayload
     * @param baseUrl
     */
    private void decreaseQuantity(Long id, AddBeerToOrderPayload addBeerToOrderPayload, String baseUrl){
        final Retrofit askBeers = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
                Snackbar.make(view, String.format("Successfully decreased quantity"), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddBeerToOrderPayload> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
    }

    /**
     * deleteItemFromCart method is responsible for deleting item from cart
     * @param orderId
     * @param beerId
     * @param baseUrl
     */
    private void deleteItemFromCart(Long orderId,Long beerId, String baseUrl){
        final Retrofit askBeers = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
                Snackbar.make(view, String.format("Successfully deleted item"), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    /**
     * addBeerToCart function is responsible for increasing cart item quantity
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
                Snackbar.make(view, String.format("Successfully increased quantity"), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddBeerToOrderPayload> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
    }


}
