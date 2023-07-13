package com.example.myapplication.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private MediatorLiveData<List<Product>> allProducts;

    public ProductRepository(Application application) {
        ProductDatabase db = ProductDatabase.getInstance(application);
        productDao = db.productDao();
        allProducts = new MediatorLiveData<>();
        allProducts.addSource(productDao.getAllProducts(), products -> allProducts.setValue(products));
    }

    public void insertProduct(Product product) {
        new InsertProductsAsyncTask(productDao).execute(product);
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    private static class InsertProductsAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private InsertProductsAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insertProduct(products[0]);
            return null;
        }
    }
}
