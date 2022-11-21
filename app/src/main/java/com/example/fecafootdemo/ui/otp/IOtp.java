package com.example.fecafootdemo.ui.otp;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 11/18/22)
 */
public interface IOtp<V extends OtpView>{
    V getView();

    void verifyCode(String code);
}
