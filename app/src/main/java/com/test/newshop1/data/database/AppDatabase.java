package com.test.newshop1.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.data.database.category.CategoryDao;
import com.test.newshop1.data.database.customer.Customer;
import com.test.newshop1.data.database.customer.CustomerDao;
import com.test.newshop1.data.database.order.Order;
import com.test.newshop1.data.database.order.OrderDao;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.data.database.product.ProductCategoryJoin;
import com.test.newshop1.data.database.product.ProductCategoryJoinDao;
import com.test.newshop1.data.database.product.ProductDao;
import com.test.newshop1.data.database.product.SimpleCategory;
import com.test.newshop1.data.database.shoppingcart.CartDao;
import com.test.newshop1.data.database.shoppingcart.CartItem;


@Database(entities = {Product.class, SimpleCategory.class, ProductCategoryJoin.class, Order.class, CartItem.class, Category.class, Customer.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "femelo";
    // For Singleton instantiation

    private static final Object LOCK = new Object();

//    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE product ADD COLUMN images TEXT");
//        }
//    };

    private static AppDatabase sInstance;
    public static AppDatabase getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }

    public abstract ProductDao productDao();
    public abstract ProductCategoryJoinDao productCategoryJoinDao();
    public abstract OrderDao orderDao();
    public abstract CartDao cartDao();
    public abstract CategoryDao categoryDao();
    public abstract CustomerDao customerDao();
}
