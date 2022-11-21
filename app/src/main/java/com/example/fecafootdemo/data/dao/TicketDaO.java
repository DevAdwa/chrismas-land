package com.example.fecafootdemo.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fecafootdemo.data.dao.entities.Ticket;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 11/18/22)
 */
@Dao
public interface TicketDaO {
    @Insert
    void insert(Ticket ticket);

    @Query("SELECT * FROM tickets")
    List<Ticket> getTicketList();

    @Query("DELETE FROM tickets")
    void resetTicketTable();
}
