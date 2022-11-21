package com.example.fecafootdemo.ui.otp;

import android.os.Handler;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.CoreDataManager;
import com.example.fecafootdemo.data.dao.entities.User;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 11/18/22)
 */
public class OtpPresenter<V extends OtpView> implements IOtp<V>{
    V v;
    private User user;
    public OtpPresenter(V v, User user) {
        this.v = v;
        this.user = user;
    }

    @Override
    public V getView() {
        return v;
    }

    @Override
    public void verifyCode(String code) {
        getView().onShowLoading();
        new Handler().postDelayed(() -> {
            getView().onHideLoading();
            if (user.getCode().equals(code)){
                CoreDataManager.getInstance().saveUser(user);
                CoreDataManager.getInstance().signedIn(true);
                getView().onStartMainActivity();
            }else{
                getView().onShowError(R.string.invalid_code);
            }
        }, 2000);
    }
}
