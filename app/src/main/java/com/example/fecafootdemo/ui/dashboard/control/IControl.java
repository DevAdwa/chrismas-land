package com.example.fecafootdemo.ui.dashboard.control;

import com.example.fecafootdemo.data.dao.entities.CardInfo;
import com.example.fecafootdemo.data.dao.entities.Ticket;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
 interface IControl<V extends ControlView> {
    void checkCode(String code, boolean isQrcode);

    void controlCardCode(String data);

    void control(Ticket cTicket);
}
