package com.example.fecafootdemo.data.dao.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Entity(tableName = "controls")
public class Control {
    @PrimaryKey(autoGenerate = true)
    private long _id;
    private String cardCode;
    private int cardCategory;
    private int cardClass;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public int getCardCategory() {
        return cardCategory;
    }

    public void setCardCategory(int cardCategory) {
        this.cardCategory = cardCategory;
    }

    public int getCardClass() {
        return cardClass;
    }

    public void setCardClass(int cardClass) {
        this.cardClass = cardClass;
    }
}
