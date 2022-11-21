package com.example.fecafootdemo.ui.loader.match;

import com.example.fecafootdemo.data.dao.entities.Game;

import java.util.ArrayList;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public interface IMatch<V extends MatchView>{
    ArrayList<Game> getGames();
}
