package com.example.fecafootdemo.ui.splash;

import android.os.Handler;

import com.example.fecafootdemo.ui.base.BasePresenter;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class SplashPresenter<V extends SplashView> extends BasePresenter<V> implements ISplash<V> {
    public SplashPresenter(V v, Handler handler) {
        super(v);
        handler.postDelayed(() -> {
            if (coreDataManager.getSignedIn()){
                getView().onStartMainActivity();
            }else {
                getView().onStartLoginActivity();
            }
        }, 2000);
    }
}
