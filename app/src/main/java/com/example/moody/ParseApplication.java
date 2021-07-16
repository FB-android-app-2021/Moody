package com.example.moody;

import com.example.moody.models.Entry;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Entry.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("FXnVKn8JG9QhkmMBESsdhLAOh8sl5P40BrtsO8A1")
                .clientKey("7PYc16iJRwv9AYcWqzOiSelqemjBIIlisDssif6f")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
