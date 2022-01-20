package com.example.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface MainDao {
    //insert query
    @Insert(onConflict= REPLACE)
    void insert(MainData mainData);

    @Delete
    void delete(MainData mainData);

    @Delete
    void reset(List<MainData>mainData);

    //update query
    @Query("UPDATE table_name SET text= :stext WHERE id= :sid")
    void update(int sid,String stext);
    //Get all data query
    @Query("SELECT * FROM table_name")
    List<MainData>getAll();





}
