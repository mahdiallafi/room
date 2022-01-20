package com.example.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MainData.class},version = 1,
        exportSchema = false)
public abstract class RoomDB  extends RoomDatabase{
  private  static RoomDB database;
  private static  String DataBase_Name="db";
  public synchronized static RoomDB getInstance(Context context){
      //check condtion
      if(database == null){
          database= Room.databaseBuilder(context.getApplicationContext(),
                  RoomDB.class,DataBase_Name).allowMainThreadQueries().fallbackToDestructiveMigration().build();
      }
      return database;
  }
  public abstract  MainDao mainDao();
}
