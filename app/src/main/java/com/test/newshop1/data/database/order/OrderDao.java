package com.test.newshop1.data.database.order;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(List<Order> order);

    @Query("SELECT * FROM `order` WHERE customerId = :customerId order by dateCreated DESC")
    LiveData<List<Order>> getOrders(int customerId);

    @Query("SELECT * FROM `order` WHERE id = :orderId")
    LiveData<Order> getOrder(String orderId);
}
