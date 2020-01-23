package com.example.beerlab.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * MyApplication class is responsible for receiving shared preferences
 */
public class MyApplication extends Application {
    public static SharedPreferences preferences;

    public SharedPreferences getSharedPrefs(){
        if(preferences == null){
            preferences = getSharedPreferences( "Auth", MODE_PRIVATE);
        }
        return preferences;
    }

}
