package com.example.fecafootdemo.ui.dashboard;

import com.example.fecafootdemo.ui.base.BaseView;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
interface IMain<V extends MainView> {
    void startControl();

    void resumeView();

    void logout();

    void exportData();

    void setNavBarInfo();
}
