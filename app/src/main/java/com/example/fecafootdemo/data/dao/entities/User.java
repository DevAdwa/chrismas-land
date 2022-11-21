package com.example.fecafootdemo.data.dao.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class User {
    private int _id;
    private String petele, ususer, penomd, pemail, uspass, usstat, pecivi, ussoci, socode, sociLib;
    private String code;
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPetele() {
        return petele;
    }

    public void setPetele(String petele) {
        this.petele = petele;
    }

    public String getUsuser() {
        return ususer;
    }

    public void setUsuser(String ususer) {
        this.ususer = ususer;
    }

    public String getPenomd() {
        return penomd;
    }

    public void setPenomd(String penomd) {
        this.penomd = penomd;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getUspass() {
        return uspass;
    }

    public void setUspass(String uspass) {
        this.uspass = uspass;
    }

    public String getUsstat() {
        return usstat;
    }

    public void setUsstat(String usstat) {
        this.usstat = usstat;
    }

    public String getPecivi() {
        return pecivi;
    }

    public void setPecivi(String pecivi) {
        this.pecivi = pecivi;
    }

    public String getUssoci() {
        return ussoci;
    }

    public void setUssoci(String ussoci) {
        this.ussoci = ussoci;
    }

    public String getSocode() {
        return socode;
    }

    public void setSocode(String socode) {
        this.socode = socode;
    }

    public String getSociLib() {
        return sociLib;
    }

    public void setSociLib(String sociLib) {
        this.sociLib = sociLib;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
