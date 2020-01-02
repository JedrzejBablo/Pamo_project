package com.example.beerlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerlab.service.AuthService;
import com.example.beerlab.service.RegisterPayload;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    public RegisterPayload registerPayload = new RegisterPayload();
    private Button registerButton;
    private RadioGroup radioGroup_age, radioGroup_gender;
    private TextView registerLogin, registerEmail, registerPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        registerButton = findViewById(R.id.register_button_id);

        registerLogin = findViewById(R.id.editText_register_nickname);
        registerPassword = findViewById(R.id.editText_register_password);
        registerEmail = findViewById(R.id.editText_register_email);
        radioGroup_age = (RadioGroup) findViewById(R.id.radioGroup_age);
        radioGroup_gender = (RadioGroup) findViewById(R.id.radioGroup_gender);

        final Intent intent = new Intent(this, LoginActivity.class);


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.31:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        registerButton.setEnabled(true);
        radioGroup_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup_gender.findViewById(checkedId);
                int index = radioGroup_gender.indexOfChild(radioButton);
                switch (index) {
                    case 0:
                        registerPayload.gender = "Mezczyzna";
                        break;
                    case 1:
                        registerPayload.gender = "Kobieta";
                        break;
                }
            }
        });

        radioGroup_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup_age.findViewById(checkedId);
                int index = radioGroup_age.indexOfChild(radioButton);
                switch (index) {
                    case 1:
                        registerPayload.dateOfBirth = "2000-01-01";
                        //  registerButton.setEnabled(true);
                        break;
                    case 0:
                        registerPayload.dateOfBirth = "2018-01-01";
                        // registerButton.setEnabled(false);
                        break;
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPayload.username = registerLogin.getText().toString();
                registerPayload.email = registerEmail.getText().toString();
                registerPayload.password = registerPassword.getText().toString();


                AuthService authService = retrofit.create(AuthService.class);
                Call<RegisterPayload> call = authService.register(registerPayload);
                call.enqueue(new Callback<RegisterPayload>() {
                    @Override
                    public void onResponse(Call<RegisterPayload> call, Response<RegisterPayload> response) {
                        System.out.println(response);
                        startActivity(intent);
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