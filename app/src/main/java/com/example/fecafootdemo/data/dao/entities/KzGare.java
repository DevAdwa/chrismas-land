package com.example.fecafootdemo.data.dao.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Entity(tableName = "kzgare")
public class KzGare {
    @PrimaryKey(autoGenerate = true)
    private long _id;
    private long kzgareid;
    private int gacode;
    private String gaanal;
    private String galibc;
    private String galibl;
    private String galiba;
    private String gapays;
    private int garesp;
    private String gavoya;
    private String gazone;
    private String galign;
    private String gaklme;
    private int gaklma;
    private String galati;
    private String galatx;
    private String galode;
    private String galodx;
    private int gaalti;
    private String gacpte;
    private int gaacti;
    private String gaucre;
    private String gadcre;
    private String gaumaj;
    private String gadmaj;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getKzgareid() {
        return kzgareid;
    }

    public void setKzgareid(long kzgareid) {
        this.kzgareid = kzgareid;
    }

    public int getGacode() {
        return gacode;
    }

    public void setGacode(int gacode) {
        this.gacode = gacode;
    }

    public String getGaanal() {
        return gaanal;
    }

    public void setGaanal(String gaanal) {
        this.gaanal = gaanal;
    }

    public String getGalibc() {
        return galibc;
    }

    public void setGalibc(String galibc) {
        this.galibc = galibc;
    }

    public String getGalibl() {
        return galibl;
    }

    public void setGalibl(String galibl) {
        this.galibl = galibl;
    }

    public String getGaliba() {
        return galiba;
    }

    public void setGaliba(String galiba) {
        this.galiba = galiba;
    }

    public String getGapays() {
        return gapays;
    }

    public void setGapays(String gapays) {
        this.gapays = gapays;
    }

    public int getGaresp() {
        return garesp;
    }

    public void setGaresp(int garesp) {
        this.garesp = garesp;
    }

    public String getGavoya() {
        return gavoya;
    }

    public void setGavoya(String gavoya) {
        this.gavoya = gavoya;
    }

    public String getGazone() {
        return gazone;
    }

    public void setGazone(String gazone) {
        this.gazone = gazone;
    }

    public String getGalign() {
        return galign;
    }

    public void setGalign(String galign) {
        this.galign = galign;
    }

    public String getGaklme() {
        return gaklme;
    }

    public void setGaklme(String gaklme) {
        this.gaklme = gaklme;
    }

    public int getGaklma() {
        return gaklma;
    }

    public void setGaklma(int gaklma) {
        this.gaklma = gaklma;
    }

    public String getGalati() {
        return galati;
    }

    public void setGalati(String galati) {
        this.galati = galati;
    }

    public String getGalatx() {
        return galatx;
    }

    public void setGalatx(String galatx) {
        this.galatx = galatx;
    }

    public String getGalode() {
        return galode;
    }

    public void setGalode(String galode) {
        this.galode = galode;
    }

    public String getGalodx() {
        return galodx;
    }

    public void setGalodx(String galodx) {
        this.galodx = galodx;
    }

    public int getGaalti() {
        return gaalti;
    }

    public void setGaalti(int gaalti) {
        this.gaalti = gaalti;
    }

    public String getGacpte() {
        return gacpte;
    }

    public void setGacpte(String gacpte) {
        this.gacpte = gacpte;
    }

    public int getGaacti() {
        return gaacti;
    }

    public void setGaacti(int gaacti) {
        this.gaacti = gaacti;
    }

    public String getGaucre() {
        return gaucre;
    }

    public void setGaucre(String gaucre) {
        this.gaucre = gaucre;
    }

    public String getGadcre() {
        return gadcre;
    }

    public void setGadcre(String gadcre) {
        this.gadcre = gadcre;
    }

    public String getGaumaj() {
        return gaumaj;
    }

    public void setGaumaj(String gaumaj) {
        this.gaumaj = gaumaj;
    }

    public String getGadmaj() {
        return gadmaj;
    }

    public void setGadmaj(String gadmaj) {
        this.gadmaj = gadmaj;
    }
}
