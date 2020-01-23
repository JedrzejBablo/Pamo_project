package com.example.beerlab.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.beerlab.R;
import com.google.android.material.snackbar.Snackbar;

public class CurrencyFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);
        initializeButtonListeners(view);
        return view;
    }

    private void initializeButtonListeners(View view) {
        Button topUpButton = view.findViewById(R.id.currency_10);
        topUpButton.setOnClickListener(this);
        topUpButton = view.findViewById(R.id.currency_20);
        topUpButton.setOnClickListener(this);
        topUpButton = view.findViewById(R.id.currency_30);
        topUpButton.setOnClickListener(this);
        topUpButton = view.findViewById(R.id.currency_40);
        topUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        String text = clickedButton.getText().toString();
        Integer amount = Integer.parseInt(text.split(" ")[1]);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Balance", 0);
        Integer currentBalance = Integer.parseInt(sharedPreferences.getString("balance", ""));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Integer newBalance = currentBalance + amount;
        editor.putString("balance", newBalance.toString());
        editor.commit();

        Snackbar.make(getView(), String.format("Success! Added %s MUG.", amount), Snackbar.LENGTH_SHORT).show();
    }
}
