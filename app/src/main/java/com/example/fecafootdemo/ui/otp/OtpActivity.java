package com.example.fecafootdemo.ui.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.ui.base.BaseActivity;
import com.google.gson.Gson;

public class OtpActivity extends BaseActivity implements OtpView, View.OnClickListener {
    OtpPresenter<OtpView> presenter;
    Button verifyBtn;
    EditText codeEdt;
    ProgressDialog progressDialog;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
        presenter = new OtpPresenter<>(this, user);
        verifyBtn = findViewById(R.id.verify_btn);
        codeEdt = findViewById(R.id.otp_code_et);
        verifyBtn.setOnClickListener(this);
        onSetup();
    }

    @Override
    public void onSetup() {
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ((TextView)findViewById(R.id.otp_code_header_tv)).setText(getString(R.string.an_otp_was_sent_to_s, user.getUsuser()));
    }

    @Override
    public void onShowLoading() {
        progressDialog.show();
    }

    @Override
    public void onHideLoading() {
        if (progressDialog != null)progressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.verify_btn){
            if (TextUtils.isEmpty(codeEdt.getText().toString())){
                codeEdt.setError(getString(R.string.field_required));
                return;
            }
            presenter.verifyCode(codeEdt.getText().toString());
        }
    }
    @Override
    public void onShowError(int message) {
        codeEdt.setError(getString(message));
    }
}