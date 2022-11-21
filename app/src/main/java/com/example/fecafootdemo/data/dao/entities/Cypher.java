package com.example.fecafootdemo.data.dao.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
@Entity(tableName = "cypher_table")
public class Cypher {
    @PrimaryKey(autoGenerate = true)
    private long _id;
    private String cypher_key;
    private String cypher_dictionary;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCypher_key() {
        return cypher_key;
    }

    public void setCypher_key(String cypher_key) {
        this.cypher_key = cypher_key;
    }

    public String getCypher_dictionary() {
        return cypher_dictionary;
    }

    public void setCypher_dictionary(String cypher_dictionary) {
        this.cypher_dictionary = cypher_dictionary;
    }
}
