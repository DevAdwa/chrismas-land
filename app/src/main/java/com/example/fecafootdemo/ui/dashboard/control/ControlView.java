package com.example.fecafootdemo.ui.dashboard.control;

import android.graphics.Bitmap;

import androidx.annotation.StringRes;

import com.example.fecafootdemo.data.dao.entities.CardInfo;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.example.fecafootdemo.ui.base.BaseView;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
interface ControlView extends BaseView {
    void onScanCodeSuccessful(String data);

    void onScanCodeFail();

    void onShowControlView(CardInfo cardInfo, String user, @StringRes int message);

    void onDismiss(String currentGame);

    void executePrinting();

    void onPrintReceipt(Bitmap b);

    void onShowSummary(Ticket ticket);
}
