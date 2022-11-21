package com.example.fecafootdemo.data.dao.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Entity(tableName = "stadium_capacities")
public class Capacity {
    @PrimaryKey(autoGenerate = true)
    private long _id;
    private String matchCode;
    private String stdPart;
    private long stdClass;
    private long stdCapacity;
    private long stdCapacityOku;

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

    public String getStdPart() {
        return stdPart;
    }

    public void setStdPart(String stdPart) {
        this.stdPart = stdPart;
    }

    public long getStdClass() {
        return stdClass;
    }

    public void setStdClass(long stdClass) {
        this.stdClass = stdClass;
    }

    public long getStdCapacity() {
        return stdCapacity;
    }

    public void setStdCapacity(long stdCapacity) {
        this.stdCapacity = stdCapacity;
    }

    public long getStdCapacityOku() {
        return stdCapacityOku;
    }

    public void setStdCapacityOku(long stdCapacityOku) {
        this.stdCapacityOku = stdCapacityOku;
    }
}
