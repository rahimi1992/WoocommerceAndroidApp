package com.test.newshop1.data.database.order;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(List<Order> order);

    @Query("SELECT * FROM `order` WHERE customerId = :customerId")
    LiveData<List<Order>> getOrders(int customerId);
}
