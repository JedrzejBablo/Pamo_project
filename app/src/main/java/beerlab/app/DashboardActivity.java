package beerlab.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import beerlab.app.model.User;
import beerlab.app.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static beerlab.app.LoginActivity.token;

public class DashboardActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.31:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<User> call = userService.checkMe(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //User user = response.body();
               // System.out.println(user.toString());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

}
