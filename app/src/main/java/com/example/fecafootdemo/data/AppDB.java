package com.example.fecafootdemo.data;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.fecafootdemo.data.dao.CapacityDao;
import com.example.fecafootdemo.data.dao.ControlDao;
import com.example.fecafootdemo.data.dao.CypherDao;
import com.example.fecafootdemo.data.dao.GameDao;
import com.example.fecafootdemo.data.dao.KzCartDao;
import com.example.fecafootdemo.data.dao.KzGareDao;
import com.example.fecafootdemo.data.dao.KzListDao;
import com.example.fecafootdemo.data.dao.SeatDao;
import com.example.fecafootdemo.data.dao.TicketDaO;
import com.example.fecafootdemo.data.dao.entities.Capacity;
import com.example.fecafootdemo.data.dao.entities.Control;
import com.example.fecafootdemo.data.dao.entities.Cypher;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.KzCart;
import com.example.fecafootdemo.data.dao.entities.KzGare;
import com.example.fecafootdemo.data.dao.entities.KzList;
import com.example.fecafootdemo.data.dao.entities.Seat;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {KzList.class, KzCart.class, KzGare.class,
        Cypher.class, Game.class, Capacity.class, Seat.class, Control.class, Ticket.class}, version = 1,
        exportSchema = true)
@TypeConverters({ObjectConverters.class})
public abstract class AppDB extends RoomDatabase {
    private static final String DB_NAME = "feca_foot_db";
    private static AppDB instance;
    private static final String TAG = AppDB.class.getSimpleName();

    public static AppDB getInstance(Context context){
        if (instance == null){
            synchronized (AppDB.class){
                instance = Room.databaseBuilder(context, AppDB.class, DB_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return instance;
    }

    //add abstract dao for this application
    public abstract CypherDao getCypherDao();
    public abstract GameDao getGameDao();
    public abstract KzGareDao getKzGareDao();
    public abstract KzListDao getKzListDao();
    public abstract KzCartDao getKzCartDao();
    public abstract CapacityDao getCapacityDao();
    public abstract SeatDao getSeatDao();
    public abstract ControlDao getControlDao();
    public abstract TicketDaO getTicketDaO();
}


//add room entities converter for custom data types
class ObjectConverters{
    @TypeConverter
    public static List<Game> stringToList(String games){
        return new Gson().fromJson(games, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    @TypeConverter
    public static String gamesToString(List<Game> games){
        return new Gson().toJson(games);
    }
}
