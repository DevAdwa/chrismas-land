package com.example.fecafootdemo.ui.login;

import android.util.Log;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public interface ILogin<V extends LoginView> {
    void authUser(String pseudo, String password);

    void showLangs();

    void change(String lang);
}
