package com.example.fecafootdemo.data.network;

import com.example.fecafootdemo.AppLoader;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
interface Apis {
    String AUTH_USER = AppLoader.CREATE("connectOtp");
    String GET_TICKET_INFO = AppLoader.CREATE("bkCtrlTicketInfo");
    String MATCH_LIST = AppLoader.CREATE("CtrlGetMatchList");
    String MATCH_INFO = AppLoader.CREATE("CtrlImportMatchInfos");
    String CHECK_ACCESS = AppLoader.CREATE("CtrlCheckAccessCard");
    String CONTROL_TICKET = AppLoader.CREATE("controlTicket");
    String EXPORT = AppLoader.CREATE("syncData");
}
