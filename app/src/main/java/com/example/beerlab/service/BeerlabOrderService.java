package com.example.beerlab.service;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.beerlab.view.MenuFragment;

public class BeerlabOrderService {

    private View view;
    private RecyclerView recyclerView;
    private BeerlabAuthService beerlabAuthService;
    private MenuFragment menuFragment;
    private Activity activity;


    public BeerlabOrderService(View view, RecyclerView recyclerView, MenuFragment menuFragment, Activity activity, Context context) {
        this.view = view;
        this.recyclerView = recyclerView;
        this.menuFragment = menuFragment;
        this.activity = activity;
        this.beerlabAuthService = new BeerlabAuthService(context);
    }



}
