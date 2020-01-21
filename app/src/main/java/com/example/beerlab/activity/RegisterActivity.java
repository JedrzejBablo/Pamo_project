package com.example.beerlab.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerlab.R;
import com.example.beerlab.activity.LoginActivity;
import com.example.beerlab.api.BeerlabAuthApi;
import com.example.beerlab.payload.RegisterPayload;
import com.example.beerlab.utils.TextValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    public RegisterPayload registerPayload = new RegisterPayload();
    private RadioGroup radioGroup_gender;
    private TextView registerLogin, registerEmail, registerPassword, errorText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        Button registerButton = findViewById(R.id.register_button_id);

        registerLogin = findViewById(R.id.editText_register_nickname);
        registerPassword = findViewById(R.id.editText_register_password);
        registerEmail = findViewById(R.id.editText_register_email);
        registerEmail.addTextChangedListener(new TextValidator(registerEmail) {
            @Override
            public void validate(TextView textView, String text) {
                String regex = "[^@]+@[^.]+\\..+";
                Pattern pattern = Pattern.compile(regex);

                Matcher matcher = pattern.matcher(text);
                if (!matcher.matches()) registerEmail.setError("Invalid email format");
        }
        });
        radioGroup_gender = findViewById(R.id.radioGroup_gender);

        final Intent intent = new Intent(this, LoginActivity.class);


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorText = findViewById(R.id.errorText);
                if (!validateForm()) return;
                switch (radioGroup_gender.getCheckedRadioButtonId()) {
                    case R.id.register_gender_male:
                        registerPayload.gender = "Mezczyzna";
                        break;
                    case R.id.register_gender_female:
                        registerPayload.gender = "Kobieta";
                        break;
                }
                registerPayload.dateOfBirth = "2000-01-01";
                registerPayload.username = registerLogin.getText().toString();
                registerPayload.email = registerEmail.getText().toString();
                registerPayload.password = registerPassword.getText().toString();

                BeerlabAuthApi beerlabAuthService = retrofit.create(BeerlabAuthApi.class);
                Call<RegisterPayload> call = beerlabAuthService.register(registerPayload);
                call.enqueue(new Callback<RegisterPayload>() {
                    @Override
                    public void onResponse(Call<RegisterPayload> call, Response<RegisterPayload> response) {
                        if (response.isSuccessful()) {
                            System.out.println(response);
                            startActivity(intent);
                        } else {
                            System.out.println(response.toString());
                            errorText = findViewById(R.id.errorText);
                            errorText.setText(R.string.user_exists);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterPayload> call, Throwable t) {
                        System.out.println(t);
                    }
                });
            }
        });
    }

    private boolean validateForm() {
        CheckBox ageCheckBox = findViewById(R.id.ageCheckBox);
        if (!ageCheckBox.isChecked()) {
            ageCheckBox.setError("Underage");
            errorText.setText(R.string.user_age);
            return false;
        }

        radioGroup_gender = findViewById(R.id.radioGroup_gender);
        if (radioGroup_gender.getCheckedRadioButtonId() == -1) {
            errorText.setText(R.string.user_gender);
            return false;
        }

        TextView[] fields = {registerPassword, registerEmail, registerLogin};
        for(TextView tw : fields) {
            String text = tw.getText().toString();
            errorText.setError("All fields should be filled");
            if(text.isEmpty()) return false;
        }
        return true;
    }
}