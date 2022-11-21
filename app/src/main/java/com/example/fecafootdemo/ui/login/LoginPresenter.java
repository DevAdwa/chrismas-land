package com.example.fecafootdemo.ui.login;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.fecafootdemo.AppLoader;
import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.KzCart;
import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.data.network.ApiCallback;
import com.example.fecafootdemo.data.network.ApiRequests;
import com.example.fecafootdemo.ui.base.BasePresenter;
import com.example.fecafootdemo.utils.ErrorUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class LoginPresenter<V extends LoginView> extends BasePresenter<V> implements ILogin<V> {
    private final String TAG = LoginPresenter.class.getSimpleName();
    final String EN                     = "English";
    final String FR                     = "Français";
    public LoginPresenter(V v) {
        super(v);
    }

    @Override
    public void authUser(String pseudo, String password) {

        if (TextUtils.isEmpty(pseudo)){
            getView().onShowError(R.string.invalid_pseudo);
        }else if (TextUtils.isEmpty(password)){
            getView().onShowError(R.string.invalid_password);
        }else {
            getView().onShowLoading(R.string.login);
            new ApiRequests().authUser(pseudo, password, coreDataManager.deviceID(), new ApiCallback() {
                @Override
                public <T> void Success(T t) {
                    getView().onUpdateLoader(R.string.finalizing);
                    getView().onHideLogin();
                    JSONObject data = (JSONObject)t;
                    try{
                        User user = new Gson().fromJson(data.getJSONObject("userInfo").toString(), User.class);
                        user.setCode(data.getString("otpCode"));
                        Log.d("data", new Gson().toJson(user));
                        getView().onStartLoaderActivity(user);
                    }catch (JSONException e){
                        getView().onShowError(e.getMessage());
                    }
                }

                @Override
                public void Failure(Exception exception) {
                    getView().onHideLogin();
                    Log.e(TAG, new Gson().toJson(exception.getMessage()));
                    getView().onShowError(R.string.unknow_error);
                }

                @Override
                public void VolleyException(VolleyError error) {
                    getView().onHideLogin();
                    Log.e(TAG, new Gson().toJson(error.getMessage()));
                    getView().onShowError(R.string.server_error);
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

    @Override
    public void showLangs() {
        String lang = coreDataManager.getLang();
        getView().onShowLang(lang.equalsIgnoreCase("en"), new String[]{EN, FR});
    }

    @Override
    public void change(String lang) {
        Log.d("lang2", lang);
        String l = lang.substring(0, 2);
        coreDataManager.setLang(l.toLowerCase());
        getView().restartActivity();
    }
}
