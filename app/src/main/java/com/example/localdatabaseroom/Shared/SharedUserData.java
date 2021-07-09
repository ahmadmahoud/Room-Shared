package com.example.localdatabaseroom.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedUserData {
    private final String SHARED_NAME = "myuserData";
    private final String USER = "user";
    private SharedPreferences preferences;
    private Gson gson;

    public SharedUserData(Context context) {
        preferences = context.getSharedPreferences(SHARED_NAME,context.MODE_PRIVATE);
        gson= new Gson();
    }

    public void setUser(SharedUser user){
        String usergson = gson.toJson(user);
        preferences.edit().putString(USER,usergson).apply();
    }

    public SharedUser getsharedUser() {
        String userGson = preferences.getString(USER,"");
        SharedUser user = gson.fromJson(userGson,SharedUser.class);
       return user;
    }

}
