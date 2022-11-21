package com.example.fecafootdemo.data.shared;

import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.User;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
interface IShared {
    void signedIn(boolean state);

    boolean getSignedIn();

    void saveUser(User user);

    User getUser();

    void clearUser();

    void setGameImported(boolean state);

    boolean isGameImported();

    void saveCurrentGame(Game game);

    Game getCurrentGame();

    void clearCurrentGame();

    void setLang(String lang);

    String getLang();
}
