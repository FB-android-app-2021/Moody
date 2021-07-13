package com.example.moody;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
       //initialize Parse backend
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("FXnVKn8JG9QhkmMBESsdhLAOh8sl5P40BrtsO8A1")
                .clientKey("7PYc16iJRwv9AYcWqzOiSelqemjBIIlisDssif6f")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
