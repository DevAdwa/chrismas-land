package com.example.fecafootdemo.ui.loader.match;

import android.util.Log;

import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.ui.base.BasePresenter;
import com.google.gson.Gson;

import java.util.ArrayList;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class MatchPresenter<V extends MatchView> extends BasePresenter<V> implements IMatch<V> {
    private final String TAG = MatchActivity.class.getSimpleName();
    public MatchPresenter(V v) {
        super(v);
    }

    @Override
    public ArrayList<Game> getGames() {
        Log.i(TAG, new Gson().toJson(coreDataManager.getGameList()));
        return (ArrayList<Game>) coreDataManager.getGameList();
    }
}
