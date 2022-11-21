package com.example.fecafootdemo.data.network;

import com.android.volley.VolleyError;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public interface ApiCallback {
    <T> void Success(T t);

    void Failure(Exception exception);

    void VolleyException(VolleyError error);

    void ServerErrorCode(int code);
}
