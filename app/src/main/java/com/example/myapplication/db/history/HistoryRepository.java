package com.example.myapplication.db.history;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HistoryRepository {
    private HistoryDao historyDao;
    private LiveData<List<History>> allHistory;

    public HistoryRepository(Application application) {
        HistoryDatabase db = HistoryDatabase.getInstance(application);
        historyDao = db.historyDao();
        allHistory = historyDao.getAllHistoryItems();
    }

    public void insertHistory(History history) {
        new InsertHistoryAsyncTask(historyDao).execute(history);
    }

    public LiveData<List<History>> getAllHistory() {
        return allHistory;
    }

    private static class InsertHistoryAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDao historyDao;

        private InsertHistoryAsyncTask(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            historyDao.insertHistory(histories[0]);
            return null;
        }
    }
}
