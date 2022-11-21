package com.example.fecafootdemo.data.network;

import org.json.JSONObject;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
interface IApis extends Apis{
    void authUser(String userName, String password, String deviceId, ApiCallback apiCallback);
    void getMatchList(String userCode, String deviceId, ApiCallback apiCallback);
    void getControl(String gameCode, String userCode, ApiCallback apiCallback);
    void checkCard(String cardCode, String qrCode, ApiCallback apiCallback);
    void export(JSONObject payload, ApiCallback apiCallback);
}
/**
 * {"cardCode":"00AEPW","cardQr":"","matchCode":"902924220330","userCode":"FOOT1"}
 */