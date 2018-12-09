package com.test.newshop1.data.database.customer;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Customer customer);

    @Query("SELECT * FROM customer WHERE isLoggedIn = :isLoggedIn LIMIT 1")
    LiveData<Customer> getLastCustomer(Boolean isLoggedIn);


    @Query("UPDATE customer SET isLoggedIn = 'false' WHERE id = :id")
    void logout(int id);
}
