package com.example.fecafootdemo.ui.loader.match;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.databinding.ActivityMainBinding;
import com.example.fecafootdemo.databinding.ActivityMatchBinding;
import com.example.fecafootdemo.ui.base.BaseActivity;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MatchActivity extends BaseActivity implements MatchView, View.OnClickListener, MatchAdapter.OnGameSelectedListener {
    ActivityMatchBinding binding;
    private MatchPresenter<MatchView> presenter;
    private MatchAdapter adapter;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new MatchPresenter<>(this);
        onSetup();
    }

    @Override
    public void onSetup() {
        binding.back.setOnClickListener(this);
        ArrayList<Game> gameArrayList = presenter.getGames();
        adapter = new MatchAdapter(gameArrayList, this);
        binding.matchesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            setResult(RESULT_CANCELED);
            onBackPressed();
        }
    }
    @Override
    public void onGameSelected(Game game) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.confirm_import, null, false);
        ((TextView)view.findViewById(R.id.message_tv)).setText(getString(R.string.are_you_sure_you_want_to_import, game.getEquipeLabel1(), game.getEquipeLabel2(), game.getDepDate()));
        view.findViewById(R.id.cancel_button).setOnClickListener(v -> {
            dialog.dismiss();
        });
        view.findViewById(R.id.confirm_button).setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent();
            intent.putExtra("game", new Gson().toJson(game));
            setResult(RESULT_OK, intent);
            onBackPressed();
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}