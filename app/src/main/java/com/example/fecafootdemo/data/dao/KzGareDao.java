package com.example.fecafootdemo.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fecafootdemo.data.dao.entities.KzGare;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Dao
public interface KzGareDao {
    @Insert
    void insertKzGare(KzGare kzGare);

    @Query("SELECT * FROM kzgare")
    List<KzGare> getKzGareList();

}
