package com.test.newshop1.data.database.product;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface ProductCategoryJoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJoins(List<ProductCategoryJoin> productCategoryJoins);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategories(List<SimpleCategory> categories);

}
