package com.example.fecafootdemo.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.fecafootdemo.R;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
public class ViewAnimationUtils {
    private final Animation animation;
    private final View scanningEffectView;
    public ViewAnimationUtils(Context context, View view){
        scanningEffectView = view;
        animation = AnimationUtils.loadAnimation(context, R.anim.scan_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void startAnimation(View view){
        scanningEffectView.setVisibility(View.VISIBLE);
        scanningEffectView.setAnimation(animation);
        view.setEnabled(false);
    }

    public void stopAnimation(View view){
        scanningEffectView.setVisibility(View.GONE);
        scanningEffectView.setAnimation(null);
        view.setEnabled(true);
    }
}
