package com.example.beerlab;

import android.app.Application;
import android.content.SharedPreferences;

public class MyApplication extends Application {
    public static SharedPreferences preferences;

    public SharedPreferences getSharedPrefs(){
        if(preferences == null){
            preferences = getSharedPreferences( "Auth", MODE_PRIVATE);
        }
        return preferences;
    }
}
