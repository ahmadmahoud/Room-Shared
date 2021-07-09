package com.example.localdatabaseroom.Shared;

import android.content.Context;
import android.content.SharedPreferences;

public class DataShared {

    private final String Shard_name = "data";
    private SharedPreferences preferences;

    private final String Name = "name";
    private final String Namename = "namelast";


    public DataShared(Context context) {
        preferences = context.getSharedPreferences(Shard_name, Context.MODE_PRIVATE);
    }

    public void setData(String name) {
        preferences.edit().putString(Name, name).apply();
    }

    public String getData() {
       return preferences.getString(Name, "no name");
    }

    public void setlastname(String lastname) {
        preferences.edit().putString(Namename, lastname).apply();
    }

    public String getlastname() {
        return preferences.getString(Namename, "no name");
    }


}
