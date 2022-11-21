package com.example.fecafootdemo.data.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.User;
import com.google.gson.Gson;

import java.util.Locale;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class SharedPref implements IShared {
    private final SharedPreferences preferences;

    public SharedPref(Context context) {
        preferences = context.getSharedPreferences("feca_foot_shared", Context.MODE_PRIVATE);
    }

    @Override
    public void signedIn(boolean state) {
        preferences.edit().putBoolean("logged_in", state).apply();
    }

    @Override
    public boolean getSignedIn() {
        return preferences.getBoolean("logged_in", false);
    }

    @Override
    public void saveUser(User user) {
        preferences.edit().putString("connected_user", new Gson().toJson(user)).apply();
    }

    @Override
    public User getUser() {
        return new Gson().fromJson(preferences.getString("connected_user", null), User.class);
    }

    @Override
    public void clearUser() {
        preferences.edit().putString("connected_user", null).apply();
    }

    @Override
    public void setGameImported(boolean state) {
        preferences.edit().putBoolean("game_imported", state).apply();
    }

    @Override
    public boolean isGameImported() {
        return preferences.getBoolean("game_imported", false);
    }

    @Override
    public void saveCurrentGame(Game game) {
        preferences.edit().putString("game", new Gson().toJson(game)).apply();
    }

    @Override
    public Game getCurrentGame() {
        return new Gson().fromJson(preferences.getString("game", null), Game.class);
    }

    @Override
    public void clearCurrentGame() {
        saveCurrentGame(null);
    }

    @Override
    public void setLang(String lang) {
        preferences.edit().putString("lang", lang).commit();
    }

    @Override
    public String getLang() {
        Log.d("lang", preferences.getString("lang", "fr"));
        return preferences.getString("lang", "fr");
    }
}
