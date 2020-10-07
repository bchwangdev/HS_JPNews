package com.bchwangdev.jpnews;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface mNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(mNews model);

    @Query("DELETE FROM news WHERE Id = :id")
    void delete(int id);

    @Query("SELECT * FROM news ORDER BY Id DESC")
    List<mNews> getAll();

    @Query("SELECT * FROM news WHERE Id = :id")
    mNews getOne(int id);

}
