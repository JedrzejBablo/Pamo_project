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
import com.example.beerlab.service.BeerlabAuthService;
import com.example.beerlab.service.BeerlabOrderService;

public class CartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private String baseUrl = "http://10.0.2.2:8081/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart,container,false);

        BeerlabAuthService beerlabAuthService = new BeerlabAuthService(getContext().getApplicationContext());
        beerlabAuthService.verifyUser(baseUrl);

        BeerlabOrderService beerlabOrderService = new BeerlabOrderService(view,this,getActivity(),getContext().getApplicationContext(),baseUrl);
        beerlabOrderService.showCartItems(baseUrl);

        return view;
    }

}
