package com.example.fecafootdemo.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

/**
 * Created By Etinge Mabian
 * Github Handler = https://github.com/Ora-Kool
 * Email = etingemabian@gmail.com
 * File created on 5/17/2021
 **/
public class FormatUtil {
    public static String formatNumber(String amount) {

        String text = amount.trim();
        int length = text.length();
        if (length > 3) {
            StringBuilder builder = new StringBuilder();
            int j = 0;
            for (int i = text.length(); i > 0; i--) {
                j++;
                builder.append(text.charAt(i - 1));
                if (j % 3 == 0 && j != length) {
                    builder.append(".");
                }
            }
            text = builder.reverse().toString().concat(" FCFA");
            return text;
        }
        return text.concat(" FCFA");
    }

    public static SpannableString adwaBlue(CharSequence text){
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE), 11, 15, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);
        return ss;
    }
}
