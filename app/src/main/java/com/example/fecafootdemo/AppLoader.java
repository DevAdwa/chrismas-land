package com.example.fecafootdemo;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.fecafootdemo.data.CoreDataManager;

import java.util.Locale;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class AppLoader extends Application {
    static {
        System.loadLibrary("fecafootdemo");
    }
    private static AppLoader instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //todo
        instance = this;
    }


    public static  synchronized  AppLoader getInstance(){
        return instance;
    }


    public static void setUpDefaultLang(Context context) {
        String lang = CoreDataManager.getInstance().getLang();
        Configuration config = new Configuration();
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    /**
     * construct api request url
     * @param endpoint
     * @return fullUrl
     */
    public static String CREATE(String endpoint) {
        return BASE_API().concat(endpoint);
    }


    /**
     * A native method that is implemented by the 'fecafootdemo' native library,
     * which is packaged with this application.
     */
    public static native String BASE_API();
}
