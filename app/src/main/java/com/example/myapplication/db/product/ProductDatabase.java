package com.example.myapplication.db.product;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

            Product product1 = new Product();
            product1.setBarcode("4870206415191");
            product1.setComposition("Нормализованное молоко, фруктовый наполнитель (вода, клубника, сахар, вес, кукурузный крахмал, миндаль, грецкий орех, фундук, концентрированный сок лимона, ароматизаторы натуральные, краситель - кармин, загуститель - пектин), сахар, йогуртовая закваска. Содержит глютен и орехи.");
            product1.setDescription("Живой питьевой йогурт из фермерского молока. Пищевая ценность в 100 г: Жиры 1,5г., Белки 2,8г., Углеводы 12,7г., Сахароза 8,3 г. Энергетическая ценность (калорийность) в 100 г: 319 кДж/76 ккал.");
            product1.setManufacturer("ТОО «DANONE BERKUT»");
            product1.setBrand("DANONE");
            product1.setCountry("Казахстан");
            product1.setColor("зеленый");
            product1.setWeight("270 мл");
            productDao.insertProduct(product1);


            Product product2 = new Product();
            product2.setBarcode("4870207313717");
            product2.setComposition("Нормализованное молоко, сахар, стабилизатор: крахмал кукурузный, ароматическая эмульсия со вкусом вишни, закваска.");
            product2.setDescription("Живой йогурт питьевой со вкусом вишни. Стандарт качества FRESH MILK, из натурального молока, без антибиотиков, без растительных жиров. Массовая доля жира 2%. Пищевая ценность в 100 г продукта: Жир 2г., белок 2,8, углеводы 12г. Энергетическая ценность (калорийность): 326 кДж/77,2 ккал.");
            product2.setManufacturer("АО «Компания ФудМастер»");
            product2.setBrand("Food Master");
            product2.setCountry("Казахстан");
            product2.setColor("зеленый");
            product2.setWeight("0,9 л");
            productDao.insertProduct(product2);



            return null;
        }
    }
}
