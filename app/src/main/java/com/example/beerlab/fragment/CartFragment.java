package com.example.beerlab.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.R;
import com.example.beerlab.service.BeerlabAuthService;
import com.example.beerlab.service.BeerlabOrderService;

public class CartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button addToCartButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart,container,false);

        BeerlabAuthService beerlabAuthService = new BeerlabAuthService(getContext().getApplicationContext());
        beerlabAuthService.verifyUser();

        BeerlabOrderService beerlabOrderService = new BeerlabOrderService(view,mRecyclerView,this,getActivity(),getContext().getApplicationContext());
        beerlabOrderService.showCartItems();

        return view;
    }

}
