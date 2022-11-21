package com.example.fecafootdemo.ui.base;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.ui.dashboard.MainActivity;
import com.example.fecafootdemo.ui.loader.LoaderActivity;
import com.example.fecafootdemo.ui.login.LoginActivity;
import com.example.fecafootdemo.utils.LangUtils;

public class BaseActivity extends AppCompatActivity implements BaseView {
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LangUtils.onCreate(this);

        setContentView(R.layout.activity_base);

    }

    @Override
    protected void onStart() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onStart();
    }

    @Override
    public void onSetup() {}

    @Override
    public void onShowLoading(@StringRes int message) {
        //progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog = createDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
       // progressDialog.setTitle(getString(message));
        //progressDialog.setTitle(null);
       // progressDialog.setMessage(getString(R.string.please_wait));
        //progressDialog.setMessage(null);
        progressDialog.show();
    }

    private static ProgressDialog createDialog(Context context){
        ProgressDialog dialog = new ProgressDialog(context);
        try{
            dialog.show();
        }catch (Exception e){}
        dialog.setCancelable(false);
        dialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressdialog);
        // dialog.setMessage(Message);
        return dialog;
    }
    @Override
    public void onHideLogin() {
        if (progressDialog != null)progressDialog.dismiss();
    }

    @Override
    public void onUpdateLoader(int message) {
        if (progressDialog != null){
            progressDialog.setTitle(getString(message));
        }
    }

    @Override
    public void onShowError(@StringRes int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
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
    public void restartActivity() {
        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onShowError(String message) {
        onHideLogin();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}