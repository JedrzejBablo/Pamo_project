package com.example.beerlab.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beerlab.R;
import com.example.beerlab.service.BeerlabAuthService;
import com.example.beerlab.service.BeerlabBeerService;

public class MenuFragment extends Fragment  {

    private RecyclerView mRecyclerView;
    private String baseUrl = "http://10.0.2.2:8081/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_menu,container,false);


        BeerlabAuthService beerlabAuthService = new BeerlabAuthService(getContext().getApplicationContext());
        beerlabAuthService.verifyUser(baseUrl);

        BeerlabBeerService beerlabBeerService = new BeerlabBeerService(view,this,getActivity(),getContext().getApplicationContext(),baseUrl);
        beerlabBeerService.showBeers(baseUrl);


        return view;

    }
}

