package com.example.fecafootdemo.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fecafootdemo.data.dao.entities.Capacity;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Dao
public interface CapacityDao {
    @Insert
    void insertCapacity(Capacity capacity);

    @Insert
    void insertCapacities(List<Capacity> capacities);

    @Query("SELECT * FROM stadium_capacities")
    List<Capacity> getCapacityList();

    @Query("DELETE FROM stadium_capacities")
    void clearCapacityTable();

    @Query("SELECT stdCapacity FROM stadium_capacities WHERE  stdClass =:clazCode")
    int findCapacity(int clazCode);
}
