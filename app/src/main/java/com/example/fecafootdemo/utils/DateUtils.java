package com.example.fecafootdemo.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();
    public static String formatDatetime(String date, String time){

        String datetime = date.concat(" ").concat(time);
        SimpleDateFormat format = new SimpleDateFormat("y-MM-d HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatDefault = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try{
            Date d = format.parse(datetime);
            assert d != null;
            return formatDefault.format(d).concat("(").concat(formatHour.format(d)).concat(")");
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date.concat("(").concat(time).concat(")");
    }
}
