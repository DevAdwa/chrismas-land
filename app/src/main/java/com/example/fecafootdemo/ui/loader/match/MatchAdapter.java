package com.example.fecafootdemo.ui.loader.match;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.utils.DateUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private final String TAG = MatchAdapter.class.getSimpleName();
    private final ArrayList<Game> gameArrayList;
    private final OnGameSelectedListener listener;

    public MatchAdapter(ArrayList<Game> gameArrayList, OnGameSelectedListener listener) {
        this.gameArrayList = gameArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(gameArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return gameArrayList != null ? gameArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_tv, team_a_name_tv, team_b_name_tv, match_date_tv, stadium_name_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            team_a_name_tv = itemView.findViewById(R.id.team_a_name_tv);
            team_b_name_tv = itemView.findViewById(R.id.team_b_name_tv);
            match_date_tv = itemView.findViewById(R.id.match_date_tv);
            stadium_name_tv = itemView.findViewById(R.id.stadium_name_tv);
        }

        public void bindView(Game game) {
            Log.i(TAG, new Gson().toJson(game));
            itemView.setOnClickListener(view -> listener.onGameSelected(game));
            title_tv.setText(game.getMatchLabel());
            team_a_name_tv.setText(game.getEquipeLabel1());
            team_b_name_tv.setText(game.getEquipeLabel2());
            match_date_tv.setText(DateUtils.formatDatetime(game.getDepDate(), game.getDeHour()));
            stadium_name_tv.setText(game.getStadiumLabel());
        }
    }

    public interface OnGameSelectedListener {
        void onGameSelected(Game game);
    }
}
