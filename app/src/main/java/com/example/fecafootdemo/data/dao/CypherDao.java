package com.example.fecafootdemo.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fecafootdemo.data.dao.entities.Cypher;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
@Dao
public interface CypherDao {
    @Insert
    void insertCypher(Cypher cypher);

    @Query("SELECT * FROM cypher_table LIMIT 1")
    Cypher getCypher();

    @Query("DELETE FROM cypher_table")
    void clearCypherTable();
}
