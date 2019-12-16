package beerlab.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import beerlab.app.service.AuthService;
import beerlab.app.service.RegisterPayload;
import beerlab.app.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    public RegisterPayload registerPayload = new RegisterPayload();
    private Button registerButton;
    private TextView registerAge;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        registerButton = findViewById(R.id.register_button);
        registerAge = findViewById(R.id.register_age);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.31:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registerPayload.username = "test";
        registerPayload.email = "test@test.pl";
        registerPayload.gender = "MALE";
        registerPayload.password = "asd";
        registerPayload.dateOfBirth = "1997-09-22";

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthService authService = retrofit.create(AuthService.class);
                Call<RegisterPayload> call = authService.register(registerPayload);
                call.enqueue(new Callback<RegisterPayload>() {
                    @Override
                    public void onResponse(Call<RegisterPayload> call, Response<RegisterPayload> response) {
                        System.out.println(response);
                    }

                    @Override
                    public void onFailure(Call<RegisterPayload> call, Throwable t) {
                        System.out.println(t);
                    }
                });
            }
        });
    }
}
