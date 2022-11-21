package com.example.fecafootdemo.ui.splash;

import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.ui.base.BaseView;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public interface SplashView extends BaseView {
    void onStartLoginActivity();
    void onStartLoaderActivity(User user);
}
