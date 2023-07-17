package com.example.myapplication.db.history;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insertHistory(History history);

    @Query("SELECT * FROM history_table ORDER BY id ASC")
    LiveData<List<History>> getAllHistoryItems();
}
