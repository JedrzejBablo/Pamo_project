package com.example.beerlab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.beerlab.model.User;
import com.example.beerlab.service.UserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Contacts.SettingsColumns.KEY;

public class MenuFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private BeerListAdapter mBeerListAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu,container,false);

        MyApplication app = (MyApplication) getContext().getApplicationContext();

        SharedPreferences sharedPreferences = app.getSharedPrefs();
        String token = sharedPreferences.getString("token", "");

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<User> call = userService.checkMe(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                System.out.println(user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t);
            }
        });

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(view.getContext());

        parseJSON();



        mRecyclerView = view.findViewById(R.id.recycler_view);
        BeerListAdapter beerListAdapter = new BeerListAdapter(this, mExampleList);
        mRecyclerView.setAdapter(beerListAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;

    }



    private void parseJSON() {
        String url = "http://10.0.2.2:8081/api/beer";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject hit = response.getJSONObject(i);

                                String beerName = hit.getString("brand");
                                String imageUrl = hit.getString("imgUrl");
                                String description = hit.getString("description");
                                Long price = hit.getLong("price");

                                mExampleList.add(new ExampleItem(imageUrl, beerName, description, price));

                            }

                            mBeerListAdapter = new BeerListAdapter(MenuFragment.this, mExampleList);
                            mRecyclerView.setAdapter(mBeerListAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(jsonArrayRequest);
    }

}
