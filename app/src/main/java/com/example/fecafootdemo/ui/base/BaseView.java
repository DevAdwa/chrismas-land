package com.example.fecafootdemo.ui.base;

import androidx.annotation.StringRes;

import com.example.fecafootdemo.data.dao.entities.User;

/*
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public interface BaseView {
    void onSetup();

    void onShowLoading(@StringRes int login);

    void onHideLogin();

    void onUpdateLoader(@StringRes int message);

    void onShowError(String message);

    void onShowError(@StringRes  int invalid_pseudo);

    void onStartMainActivity();

    void onStartLoginActivity();

    void onStartLoaderActivity(User user);

    void restartActivity();
}
