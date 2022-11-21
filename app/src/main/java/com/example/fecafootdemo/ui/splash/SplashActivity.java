package com.example.fecafootdemo.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.databinding.ActivitySplashBinding;
import com.example.fecafootdemo.ui.base.BaseActivity;
import com.example.fecafootdemo.ui.loader.LoaderActivity;
import com.example.fecafootdemo.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity implements SplashView {
    private SplashPresenter<SplashView> presenter;
    private ActivitySplashBinding binding;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new SplashPresenter<>(this, handler);
    }

    @Override
    public void onSetup() {
        super.onSetup();
    }

    @Override
    public void onStartLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onStartLoaderActivity(User user) {
        startActivity(new Intent(this, LoaderActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}