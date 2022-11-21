package com.example.fecafootdemo.ui.loader;

import android.util.Log;

import com.android.volley.VolleyError;
import com.example.fecafootdemo.AppLoader;
import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.Capacity;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.data.network.ApiCallback;
import com.example.fecafootdemo.data.network.ApiRequests;
import com.example.fecafootdemo.ui.base.BasePresenter;
import com.example.fecafootdemo.utils.ErrorUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class LoaderPresenter<V extends LoaderView> extends BasePresenter<V> implements ILoader<V> {
    private final String TAG = LoaderPresenter.class.getSimpleName();
    private final User user;
    public LoaderPresenter(V v) {
        super(v);
        user = coreDataManager.getUser();
    }

    @Override
    public void logout() {
        coreDataManager.saveUser(null);
        coreDataManager.signedIn(false);
        coreDataManager.clearGameTable();
        coreDataManager.clearCapacityTable();
        coreDataManager.setGameImported(false);
        coreDataManager.clearCurrentGame();

        getView().onBackToLogin();
    }

    @Override
    public void loadGames(Game game) {
        Log.i(TAG, new Gson().toJson(game));
        getView().onShowLoading(R.string.loading_match);
        new ApiRequests().getControl(game.getMatchCode(), user.getUsuser(), new ApiCallback() {
            @Override
            public <T> void Success(T t) {
                getView().onHideLogin();
                if (t instanceof JSONArray){
                    JSONArray data = (JSONArray) t;
                    List<Capacity> capacities = new Gson().fromJson(data.toString(), new TypeToken<ArrayList<Capacity>>(){}.getType());
                    coreDataManager.insertCapacity(capacities);
                    coreDataManager.setGameImported(true);
                    coreDataManager.saveCurrentGame(game);
                    getView().onStartMainActivity();
                }
                Log.i(TAG, new Gson().toJson(t));
            }

            @Override
            public void Failure(Exception exception) {
                getView().onHideLogin();
                Log.e(TAG, new Gson().toJson(exception.getMessage()));
                getView().onShowError(exception.getMessage());
            }

            @Override
            public void VolleyException(VolleyError error) {
                getView().onHideLogin();
                Log.e(TAG, new Gson().toJson(error.getMessage()));
                getView().onShowError(error.getMessage());
            }

            @Override
            public void ServerErrorCode(int code) {
                String message = ErrorUtils.getMessage(code, AppLoader.getInstance());
                getView().onShowError(message);
                Log.e(TAG, "" + code);
            }
        });
    }
}
