package com.example.fecafootdemo.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fecafootdemo.data.dao.entities.Game;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Dao
public interface GameDao {
    @Insert
    void insertGame(Game game);

    @Insert
    void insertGames(List<Game> games);

    @Query("SELECT * FROM games")
    List<Game> getGameList();

    @Query("DELETE FROM games")
    void clearGameTable();
}
