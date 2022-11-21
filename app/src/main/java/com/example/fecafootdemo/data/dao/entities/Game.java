package com.example.fecafootdemo.data.dao.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
@Entity(tableName = "games")
public class Game {
    @PrimaryKey(autoGenerate = true)
    private long _id;
    private String matchLabel;
    private String matchCode;
    private String depDate;
    private String deHour;
    private String stadiumCode;
    private String stadiumLabel;
    private String equipeCode1;
    private String equipeLabel1;
    private String equipeLabelLong1;
    private String equipeCode2;
    private String equipeLabel2;
    private String equipeLabelLong2;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getMatchLabel() {
        return matchLabel;
    }

    public void setMatchLabel(String matchLabel) {
        this.matchLabel = matchLabel;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getDeHour() {
        return deHour;
    }

    public void setDeHour(String deHour) {
        this.deHour = deHour;
    }

    public String getStadiumCode() {
        return stadiumCode;
    }

    public void setStadiumCode(String stadiumCode) {
        this.stadiumCode = stadiumCode;
    }

    public String getStadiumLabel() {
        return stadiumLabel;
    }

    public void setStadiumLabel(String stadiumLabel) {
        this.stadiumLabel = stadiumLabel;
    }

    public String getEquipeCode1() {
        return equipeCode1;
    }

    public void setEquipeCode1(String equipeCode1) {
        this.equipeCode1 = equipeCode1;
    }

    public String getEquipeLabel1() {
        return equipeLabel1;
    }

    public void setEquipeLabel1(String equipeLabel1) {
        this.equipeLabel1 = equipeLabel1;
    }

    public String getEquipeCode2() {
        return equipeCode2;
    }

    public void setEquipeCode2(String equipeCode2) {
        this.equipeCode2 = equipeCode2;
    }

    public String getEquipeLabel2() {
        return equipeLabel2;
    }

    public void setEquipeLabel2(String equipeLabel2) {
        this.equipeLabel2 = equipeLabel2;
    }

    public String getEquipeLabelLong1() {
        return equipeLabelLong1;
    }

    public void setEquipeLabelLong1(String equipeLabelLong1) {
        this.equipeLabelLong1 = equipeLabelLong1;
    }

    public String getEquipeLabelLong2() {
        return equipeLabelLong2;
    }

    public void setEquipeLabelLong2(String equipeLabelLong2) {
        this.equipeLabelLong2 = equipeLabelLong2;
    }
}
