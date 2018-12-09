package com.test.newshop1.data.database.order;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.test.newshop1.data.database.product.Product;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Order order);

    @Query("SELECT * FROM `order`")
    List<Order> getOrders();
}
