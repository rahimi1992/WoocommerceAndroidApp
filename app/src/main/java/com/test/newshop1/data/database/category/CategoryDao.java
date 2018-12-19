package com.test.newshop1.data.database.category;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Category> categories);

    @Query("SELECT * FROM category WHERE count > 0")
    List<Category> getCategories();


}
