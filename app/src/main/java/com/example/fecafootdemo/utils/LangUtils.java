package com.example.fecafootdemo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.example.fecafootdemo.data.CoreDataManager;
import com.google.gson.Gson;

import java.util.Locale;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/31/22)
 */
public class LangUtils {
    public static void onCreate(Context context){
        String lang = CoreDataManager.getInstance().getLang();
        Configuration configuration = new Configuration();
        Locale locale = new Locale("fr_Fr");
        Log.d("locale", new Gson().toJson(locale));
        Locale loc = new Locale(lang);
        Locale.setDefault(locale);
        configuration.locale = loc;
        context.getResources().updateConfiguration(configuration, null);
    }
}
