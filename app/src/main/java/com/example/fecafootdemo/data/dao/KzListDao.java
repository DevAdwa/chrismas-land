package com.example.fecafootdemo.data.dao;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fecafootdemo.data.dao.entities.KzList;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Dao
public interface KzListDao {
    @Insert
    void insertKzList(KzList kzList);

    @Query("SELECT * FROM kzlist")
    List<KzList> getKzLists();

    @Query("SELECT * FROM kzlist WHERE lilibc = :qrcate")
    KzList findCategoryBy(String qrcate);
}
