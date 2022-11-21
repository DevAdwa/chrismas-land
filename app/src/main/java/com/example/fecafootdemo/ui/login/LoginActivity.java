package com.example.fecafootdemo.ui.login;

import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.databinding.ActivityLoginBinding;
import com.example.fecafootdemo.ui.base.BaseActivity;
import com.example.fecafootdemo.ui.otp.OtpActivity;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {
    private LoginPresenter<LoginView> presenter;
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new LoginPresenter<>(this);
        binding.loginBtn.setOnClickListener(this);

        onSetup();
    }

    @Override
    public void onSetup() {
        super.onSetup();
        binding.languageTv.setOnClickListener(v -> {
            presenter.showLangs();
        });
    }

    @Override
    public void onClick(View view) {
        String pseudo = binding.userEdt.getText().toString();
        String password = binding.passwordEdt.getText().toString();
        presenter.authUser(pseudo, password);
    }

    @Override
    public void onStartLoaderActivity(User user) {
        startActivity(new Intent(this, OtpActivity.class)
                .putExtra("user", new Gson().toJson(user)));
    }

    @Override
    public void onShowLang(boolean en, String[] langs) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.language));
        builder.setSingleChoiceItems(langs, en ? 0 : 1, (dialog, which) -> {
            presenter.change(langs[which]);
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}