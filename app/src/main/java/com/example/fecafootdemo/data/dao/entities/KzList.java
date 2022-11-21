package com.example.fecafootdemo.data.dao.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Entity(tableName = "kzlist")
public class KzList {
    @PrimaryKey(autoGenerate = true)
    private long _id;
    private long kzlistid;
    private String licode;
    private String lilibc;
    private String lilibl;
    private String liliba;
    private String livaid;
    private int liacti;
    private String liucre;
    private String lidcre;
    private String liumaj;
    private String lidmaj;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getKzlistid() {
        return kzlistid;
    }

    public void setKzlistid(long kzlistid) {
        this.kzlistid = kzlistid;
    }

    public String getLicode() {
        return licode;
    }

    public void setLicode(String licode) {
        this.licode = licode;
    }

    public String getLilibc() {
        return lilibc;
    }

    public void setLilibc(String lilibc) {
        this.lilibc = lilibc;
    }

    public String getLilibl() {
        return lilibl;
    }

    public void setLilibl(String lilibl) {
        this.lilibl = lilibl;
    }

    public String getLiliba() {
        return liliba;
    }

    public void setLiliba(String liliba) {
        this.liliba = liliba;
    }

    public String getLivaid() {
        return livaid;
    }

    public void setLivaid(String livaid) {
        this.livaid = livaid;
    }

    public int getLiacti() {
        return liacti;
    }

    public void setLiacti(int liacti) {
        this.liacti = liacti;
    }

    public String getLiucre() {
        return liucre;
    }

    public void setLiucre(String liucre) {
        this.liucre = liucre;
    }

    public String getLidcre() {
        return lidcre;
    }

    public void setLidcre(String lidcre) {
        this.lidcre = lidcre;
    }

    public String getLiumaj() {
        return liumaj;
    }

    public void setLiumaj(String liumaj) {
        this.liumaj = liumaj;
    }

    public String getLidmaj() {
        return lidmaj;
    }

    public void setLidmaj(String lidmaj) {
        this.lidmaj = lidmaj;
    }
}
