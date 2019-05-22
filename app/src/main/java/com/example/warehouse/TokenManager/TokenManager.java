package com.example.warehouse.TokenManager;

import android.content.SharedPreferences;

import com.example.warehouse.Model.AccessToken;

public class TokenManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static TokenManager INSTANCE = null;

    private TokenManager(SharedPreferences preferences){
        this.preferences = preferences;
        this.editor = preferences.edit();
    }

    public static synchronized TokenManager getInstance(SharedPreferences preferences){
        if(INSTANCE == null){
            INSTANCE = new TokenManager(preferences);
        }
        return INSTANCE;
    }

    public void saveToken(AccessToken token){
        editor.putString("access_token", token.getAccessToken()).commit();
    }

    public void deleteToken(){
        editor.remove("access_token").commit();
    }

    public AccessToken getToken(){
        AccessToken token = new AccessToken();
        token.setAccessToken(preferences.getString("access_token", null));
        return token;
    }

}
