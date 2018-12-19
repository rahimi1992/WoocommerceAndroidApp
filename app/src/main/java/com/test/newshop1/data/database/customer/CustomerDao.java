package com.test.newshop1.data.database.customer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Customer customer);

    @Query("SELECT * FROM customer WHERE isLoggedIn = :isLoggedIn LIMIT 1")
    LiveData<Customer> getLastCustomer(Boolean isLoggedIn);


    @Query("UPDATE customer SET isLoggedIn = 'false' WHERE id = :id")
    void logout(int id);
}
