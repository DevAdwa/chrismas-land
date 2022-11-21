package com.example.fecafootdemo.ui.loader;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.databinding.ActivityLoaderBinding;
import com.example.fecafootdemo.ui.base.BaseActivity;
import com.example.fecafootdemo.ui.loader.match.MatchActivity;
import com.example.fecafootdemo.ui.login.LoginActivity;
import com.google.gson.Gson;

public class LoaderActivity extends BaseActivity implements LoaderView, View.OnClickListener {
    private final String TAG = LoaderActivity.class.getSimpleName();
    ActivityLoaderBinding binding;
    private LoaderPresenter<LoaderView> presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new LoaderPresenter<>(this);
        setSupportActionBar(binding.toolbar);
        onSetup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_menu_item){
            presenter.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSetup() {
        binding.pulse.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.pulse.startRippleAnimation();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pulse){
            launcher.launch(new Intent(this, MatchActivity.class));
        }
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null){
                        Log.i(TAG, "Got result back");
                        Game game = new Gson().fromJson(result.getData().getStringExtra("game"), Game.class);
                        presenter.loadGames(game);
                    }else {
                        Log.i(TAG, "no result");
                    }
                }
            }
    );

    @Override
    public void onBackToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}