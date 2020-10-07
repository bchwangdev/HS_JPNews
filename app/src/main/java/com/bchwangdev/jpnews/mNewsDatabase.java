package com.bchwangdev.jpnews;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {mNews.class}, version = 1, exportSchema = false)
public abstract class mNewsDatabase extends RoomDatabase {
    public abstract mNewsDao mNewsDao();

}

