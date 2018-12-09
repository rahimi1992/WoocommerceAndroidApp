package com.test.newshop1.data.database.product;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;



@Dao
public interface ProductDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Product> products);



    @Query("SELECT * FROM product INNER JOIN product_category_join " +
            "ON product.id = product_category_join.productId " +
            "WHERE product_category_join.categoryId = :categoryId " +
            "ORDER BY dateCreated DESC")
    DataSource.Factory<Integer, Product> getProducts(int categoryId);

    @Query("SELECT * FROM product ORDER BY dateCreated DESC LIMIT :offset , :perPage")
    List<Product> getProducts(int perPage, int offset);

    @Query("SELECT * FROM product INNER JOIN product_category_join " +
            "ON product.id = product_category_join.productId " +
            "WHERE product_category_join.categoryId = :categoryId " +
            "ORDER BY dateCreated DESC LIMIT :offset , :perPage")
    List<Product> getProducts(int perPage, int offset, int categoryId);


//    @Query("SELECT * FROM product ORDER BY dateCreated DESC LIMIT :offset , :perPage")
//    List<Product> getProducts(int perPage, int offset, int category);


    @Query("SELECT * FROM product WHERE id = :productId")
    Product getProduct(int productId);

    @Query("SELECT * FROM product WHERE id IN (:ids)")
    LiveData<List<Product>> getRelatedProducts(List<Integer> ids);
}
