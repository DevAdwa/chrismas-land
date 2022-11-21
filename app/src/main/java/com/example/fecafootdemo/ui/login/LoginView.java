package com.example.fecafootdemo.ui.login;

import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.ui.base.BaseView;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public interface LoginView extends BaseView {
    void onStartLoaderActivity(User user);

    void onShowLang(boolean en, String[] strings);
}
