package com.example.beerlab.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.R;
import com.example.beerlab.api.BeerlabOrderApi;
import com.example.beerlab.model.Order;
import com.example.beerlab.service.BeerlabAuthService;
import com.example.beerlab.service.BeerlabOrderService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private String baseUrl = "http://10.0.2.2:8081/";
    private Button confirmOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart,container,false);

        final BeerlabAuthService beerlabAuthService = new BeerlabAuthService(getContext().getApplicationContext());
        beerlabAuthService.verifyUser(baseUrl);

        final BeerlabOrderService beerlabOrderService = new BeerlabOrderService(view,this,getActivity(),getContext().getApplicationContext(),baseUrl);
        beerlabOrderService.showCartItems(baseUrl);

        confirmOrder = view.findViewById(R.id.button_confirmOrder);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Retrofit askOrder = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                BeerlabOrderApi orderService = askOrder.create(BeerlabOrderApi.class);

                Call<Order> callOrder = orderService.confirmOrder(beerlabAuthService.getToken());

                callOrder.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (!response.isSuccessful()){
                            System.out.println("Wooooow, something went wrong ! :( " + response.code());
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }


                });
            }
        });

        return view;
    }

}
