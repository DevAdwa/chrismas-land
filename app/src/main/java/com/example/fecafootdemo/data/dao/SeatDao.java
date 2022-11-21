package com.example.fecafootdemo.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fecafootdemo.data.dao.entities.Seat;

import java.util.List;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
@Dao
public interface SeatDao {
    @Insert
    void insertSeat(Seat seat);

    @Insert
    void insertSeats(List<Seat> seats);

    @Query("SELECT * FROM seats")
    List<Seat> getSeatList();

    @Query("DELETE FROM seats")
    void clearSeatsTable();

    @Query("SELECT seatNumber FROM seats ORDER BY RANDOM() LIMIT 1")
    String getRandomSeat();
}
