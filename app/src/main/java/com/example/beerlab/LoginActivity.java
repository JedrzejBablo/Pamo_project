package com.example.beerlab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerlab.service.AuthService;
import com.example.beerlab.service.LoginPayload;
import com.example.beerlab.utils.TextValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button login;
    LoginPayload loginPayload = new LoginPayload();
    TextView emailField, passwordField, register;
    public static String token ="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login = findViewById(R.id.button_login_id);
        emailField = findViewById(R.id.login_act_email_id);
        passwordField = findViewById(R.id.login_actv_password_id);
        register = findViewById(R.id.textView_registerHere);
        final Intent intent = new Intent(this, DashboardActivity.class);

        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.31:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        login.setEnabled(false);
        validateEmail();
        validatePassword();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPayload.email = emailField.getText().toString();
                loginPayload.password = passwordField.getText().toString();
                AuthService authService = retrofit.create(AuthService.class);
                Call<LoginPayload> call = authService.login(loginPayload);
                call.enqueue(new Callback<LoginPayload>() {
                    @Override
                    public void onResponse(Call<LoginPayload> call, Response<LoginPayload> response) {
                        System.out.println(response.headers().get("X-Auth-Token"));
                        token = response.headers().get("X-Auth-Token");
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<LoginPayload> call, Throwable t) {
                        System.out.println(t);
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterActivity();
            }
        });

    }

    private void validateEmail() {
        emailField.addTextChangedListener(new TextValidator(emailField) {
            @Override
            public void validate(TextView textView, String text) {
                String emailValue = emailField.getText().toString();
                if (emailValue.isEmpty()) {
                    emailField.setError("To pole nie może być puste");
                    login.setEnabled(false);
                } else {
                    login.setEnabled(true);
                }
            }
        });
    }

    private void validatePassword() {
        passwordField.addTextChangedListener(new TextValidator(passwordField) {
            @Override
            public void validate(TextView textView, String text) {
                String emailValue = passwordField.getText().toString();
                if (emailValue.isEmpty()) {
                    passwordField.setError("To pole nie może być puste");
                    login.setEnabled(false);
                } else {
                    login.setEnabled(true);
                }
            }
        });
    }

    private void validate() {
        if (passwordField.getText().toString().isEmpty() && emailField.getText().toString().isEmpty()) {
            login.setEnabled(true);
        }
    }

    private void launchRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
