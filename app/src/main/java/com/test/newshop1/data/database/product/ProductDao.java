package com.test.newshop1.data.database.product;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


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

    @Query("SELECT * FROM product WHERE description LIKE :query OR name LIKE :query")
    DataSource.Factory<Integer, Product> searchProducts(String query);

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
