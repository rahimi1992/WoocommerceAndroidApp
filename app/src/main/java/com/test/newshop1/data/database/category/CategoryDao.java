package com.test.newshop1.data.database.category;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Category> categories);

    @Query("SELECT * FROM category WHERE count > 0")
    List<Category> getCategories();


}
