package com.example.fecafootdemo.data.dao.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Entity(tableName = "kzcart")
public class KzCart {
    @PrimaryKey(autoGenerate = true)
    private long _id;
    private String kzcartid;
    private String qrcode;
    private String qrqrcd;
    private String qrpers;
    private String qrclas;
    private String qrcate;
    private String qrloca;
    private String qrgare;
    private String qrnbre;
    private String qrnbdu;
    private String qrddeb;
    private String qrdfin;
    private String qretat;
    private int qracti;
    private String qrucre;
    private String qrdcre;
    private String qrumaj;
    private String qrdmaj;
    private String penomd;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getKzcartid() {
        return kzcartid;
    }

    public void setKzcartid(String kzcartid) {
        this.kzcartid = kzcartid;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrqrcd() {
        return qrqrcd;
    }

    public void setQrqrcd(String qrqrcd) {
        this.qrqrcd = qrqrcd;
    }

    public String getQrpers() {
        return qrpers;
    }

    public void setQrpers(String qrpers) {
        this.qrpers = qrpers;
    }

    public String getQrclas() {
        return qrclas;
    }

    public void setQrclas(String qrclas) {
        this.qrclas = qrclas;
    }

    public String getQrcate() {
        return qrcate;
    }

    public void setQrcate(String qrcate) {
        this.qrcate = qrcate;
    }

    public String getQrloca() {
        return qrloca;
    }

    public void setQrloca(String qrloca) {
        this.qrloca = qrloca;
    }

    public String getQrgare() {
        return qrgare;
    }

    public void setQrgare(String qrgare) {
        this.qrgare = qrgare;
    }

    public String getQrnbre() {
        return qrnbre;
    }

    public void setQrnbre(String qrnbre) {
        this.qrnbre = qrnbre;
    }

    public String getQrnbdu() {
        return qrnbdu;
    }

    public void setQrnbdu(String qrnbdu) {
        this.qrnbdu = qrnbdu;
    }

    public String getQrddeb() {
        return qrddeb;
    }

    public void setQrddeb(String qrddeb) {
        this.qrddeb = qrddeb;
    }

    public String getQrdfin() {
        return qrdfin;
    }

    public void setQrdfin(String qrdfin) {
        this.qrdfin = qrdfin;
    }

    public String getQretat() {
        return qretat;
    }

    public void setQretat(String qretat) {
        this.qretat = qretat;
    }

    public int getQracti() {
        return qracti;
    }

    public void setQracti(int qracti) {
        this.qracti = qracti;
    }

    public String getQrucre() {
        return qrucre;
    }

    public void setQrucre(String qrucre) {
        this.qrucre = qrucre;
    }

    public String getQrdcre() {
        return qrdcre;
    }

    public void setQrdcre(String qrdcre) {
        this.qrdcre = qrdcre;
    }

    public String getQrumaj() {
        return qrumaj;
    }

    public void setQrumaj(String qrumaj) {
        this.qrumaj = qrumaj;
    }

    public String getQrdmaj() {
        return qrdmaj;
    }

    public void setQrdmaj(String qrdmaj) {
        this.qrdmaj = qrdmaj;
    }

    public String getPenomd() {
        return penomd;
    }

    public void setPenomd(String penomd) {
        this.penomd = penomd;
    }
}
