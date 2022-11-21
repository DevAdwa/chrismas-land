package com.example.fecafootdemo.ui.otp;

import com.example.fecafootdemo.ui.base.BaseView;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 11/18/22)
 */
public interface OtpView {
    void onSetup();

    void onShowLoading();

    void onHideLoading();

    void onStartMainActivity();

    void onShowError(int invalid_code);
}
