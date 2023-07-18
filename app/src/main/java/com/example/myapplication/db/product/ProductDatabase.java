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

            Product product3 = new Product();
            product3.setBarcode("4870055002696");
            product3.setComposition("Молоко нормализованное, витамин D3.");
            product3.setDescription("Молоко питьевое ультрапастеризованное «Lactel», обогащенное витамином D3, массовая доля жира 3,2%.\nДля детского питания, для детей с 6 лет и старше.\n" +
                    "Питательная (пищевая) ценность на 100 г продукта: Жир 3,2 г., Белок 2,8 г., Углеводы 4,5 г., Витамин D3 0,5 мкг*., Калорийность 58 ккал., Энергетическая ценность 242 кДж.\n");
            product3.setManufacturer("АО «Компания ФудМастер»");
            product3.setBrand("Lactel");
            product3.setCountry("Казахстан");
            product3.setColor("синий");
            product3.setWeight("1 мл");
            productDao.insertProduct(product3);

            Product product4 = new Product();
            product4.setBarcode("4870003431660");
            product4.setComposition("Цельное молоко, обезжиренное молоко.");
            product4.setDescription("Питьевое молоко ультрапастеризованное изготовленное из фермерского молока.  Массовая доля жира 3,2%. Пищевая ценность в 100 г продукта: Белки 2,8г., Углеводы 4,7г., Жиры 3,2г. Энергетическая ценность не менее 58,8 ккал/246,2 кДж.");
            product4.setManufacturer("ТОО «Raimbek Agro»");
            product4.setBrand("Айналайын");
            product4.setCountry("Казахстан");
            product4.setColor("синий");
            product4.setWeight("1 л");
            productDao.insertProduct(product4);

            Product product5 = new Product();
            product5.setBarcode("4870206414996");
            product5.setComposition("Нормализованное молоко, восстановленное молоко из сухого молока, наполнитель (вода, персик, сахар, кукурузный крахмал, ароматизатор натуральным, лимонный сок концентрированный, загуститель - пектины, краситель - каротины), сахар, йогуртовая закваска, бифидобактерия ActiRegularis.");
            product5.setDescription("Биойогурт «Активиа питьевая», обогащенный бифидобактериями ActiRegularis, с персиком. Пищевая ценность на 100 г : Белки - 2,8г., Жиры – 2,1 г., Углеводы 11 г. в тч. сахароза – 6,5 г., энергетическая ценность/калорийность - 312 кДж/74 ккал.");
            product5.setManufacturer("ТОО «Danone Bertal»");
            product5.setBrand("АКТИВИА");
            product5.setCountry("Казахстан");
            product5.setColor("синий");
            product5.setWeight("1 л");
            productDao.insertProduct(product5);

            Product product6 = new Product();
            product6.setBarcode("4870004063945");
            product6.setComposition("Нормализованное молоко, сухое цельное молоко, сухое обезжиренное молоко, закваски с бифидобактериями и бактериями болгарской палочки, сахар, фруктово-ягодный наполнитель клубника (сахар, загуститель пектин, крахмал, регулятор кислотности лимонная кислота, красители натуральные, пищевой ароматизатор).\n");
            product6.setDescription("Биойогурт питьевой с клубникой без растительных жиров, массовая доля жира 1,5%.  Пищевая ценность в 100 г : Жир 1,5 г., Белок 2,8 г., Углеводы 11 г. в т.ч. сахароза 6,5г., Энергетическая ценность (калорийность): 289 кДж/69 ккал.\n" +
                    "Мы получаем молоко на первой в Средней Азии роботизированной ферме. Сохраняем полезные для организма кальций, витамин А и витамины группы В. Используем только современные технологии переработки и упаковки.\n");
            product6.setManufacturer("ТОО «Восток-Молоко»");
            product6.setBrand("Восток-Молоко");
            product6.setCountry("Казахстан");
            product6.setColor("красный");
            product6.setWeight("450 мл");
            productDao.insertProduct(product6);

            Product product7 = new Product();
            product4.setBarcode("4870003431660");
            product4.setComposition("Цельное молоко, обезжиренное молоко.");
            product4.setDescription("");
            product4.setManufacturer("ТОО «Raimbek Agro»");
            product4.setBrand("Айналайын");
            product4.setCountry("Казахстан");
            product4.setColor("синий");
            product4.setWeight("1 л");
            productDao.insertProduct(product4);


            return null;
        }
    }
}
