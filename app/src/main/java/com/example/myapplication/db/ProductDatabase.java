package com.example.myapplication.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    private static ProductDatabase instance;

    public abstract ProductDao productDao();

    public static synchronized ProductDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, "product_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductDao productDao;

        private PopulateDbAsyncTask(ProductDatabase db) {
            productDao = db.productDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("TAG", "inserting... ");
            productDao.insertProduct(new Product("4870206415191",
                    "Нормализованное молоко, фруктовый наполнитель (вода, клубника, сахар, вес, кукурузный крахмал, миндаль, грецкий орех, фундук, концентрированный сок лимона, ароматизаторы натуральные, краситель - кармин, загуститель - пектин), сахар, йогуртовая закваска. Содержит глютен и орехи."
                    , "Живой питьевой йогурт из фермерского молока. Пищевая ценность в 100 г: Жиры 1,5г., Белки 2,8г., Углеводы 12,7г., Сахароза 8,3 г. Энергетическая ценность (калорийность) в 100 г: 319 кДж/76 ккал."
                    , "ТОО «DANONE BERKUT»"
                    , "DANONE"
                    , "Казахстан"
                    , "зеленый"
                    , "270 мл"));
            productDao.insertProduct(new Product("4870207313717",
                    "Нормализованное молоко, сахар, стабилизатор: крахмал кукурузный, ароматическая эмульсия со вкусом вишни, закваска."
                    , "Живой йогурт питьевой со вкусом вишни. Стандарт качества FRESH MILK, из натурального молока, без антибиотиков, без растительных жиров. Массовая доля жира 2%. Пищевая ценность в 100 г продукта: Жир 2г., белок 2,8, углеводы 12г. Энергетическая ценность (калорийность): 326 кДж/77,2 ккал."
                    , "АО «Компания ФудМастер»"
                    , "Food Master"
                    , "Казахстан"
                    , "зеленый"
                    , "0,9 л"));


            return null;
        }
    }
}
