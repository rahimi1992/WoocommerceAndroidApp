package com.test.newshop1.data.database.product;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface ProductCategoryJoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJoins(List<ProductCategoryJoin> productCategoryJoins);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategories(List<SimpleCategory> categories);

}
