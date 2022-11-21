package com.example.fecafootdemo.ui.dashboard;

import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.ui.base.BaseView;

import java.util.ArrayList;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
interface MainView extends BaseView {
    void onStartControlActivity();

    void onResumeView();

    void onNavBarSetup(User user, ArrayList<Ticket> tickets);
}
