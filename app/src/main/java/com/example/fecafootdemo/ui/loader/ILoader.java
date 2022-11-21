package com.example.fecafootdemo.ui.loader;

import com.example.fecafootdemo.data.dao.entities.Game;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public interface ILoader <V extends LoaderView> {
    void logout();

    void loadGames(Game game);
}
