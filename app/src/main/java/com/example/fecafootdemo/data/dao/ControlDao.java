package com.example.fecafootdemo.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fecafootdemo.data.dao.entities.Control;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Dao
public interface ControlDao {
    @Insert
    void insertControl(Control control);

    @Query("SELECT * FROM controls")
    List<Control> getControls();

    @Query("SELECT * FROM controls WHERE cardCode =:code")
    List<Control> getControlsByCode(String code);

    @Query("SELECT cardCode FROM controls WHERE cardCategory = :cardCategory")
    List<String> findAllCardCategoryByCategoryId(int cardCategory);

    @Query("SELECT cardCode FROM controls WHERE cardClass = :cardClz")
    List<String> findAllCardClassByCardId(int cardClz);

    @Query("DELETE FROM controls")
    void clearControlsTable();
}
