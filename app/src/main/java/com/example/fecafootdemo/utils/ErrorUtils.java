package com.example.fecafootdemo.utils;

import android.content.Context;
import android.util.Log;

import com.example.fecafootdemo.R;
import com.google.gson.Gson;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class ErrorUtils {
    public static String getMessage(int code, Context context){
        int[] codes = context.getResources().getIntArray(R.array.server_error_codes);
        Log.d("codes", new Gson().toJson(codes));
        int index;
        for (int i = 0; i < codes.length; i++) {
            if (code == codes[i]){
                Log.d("found", "" + i);
                index = i;
                return context.getResources().getStringArray(R.array.server_error_messages)[index];
            }
            Log.d("index", "" + i);
        }
        return context.getResources().getString(R.string.unknow_error);
    }
}
