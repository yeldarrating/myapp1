package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.db.history.History;
import com.example.myapplication.db.history.HistoryRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository historyRepository;
    private LiveData<List<History>> allHistory;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        historyRepository = new HistoryRepository(application);
        allHistory = historyRepository.getAllHistory();
    }

    public void insertHistory(History history) {
        historyRepository.insertHistory(history);
    }

    public LiveData<List<History>> getAllHistory() {
        return allHistory;
    }
}
