package com.test.newshop1.data.database.product;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;



@Dao
public interface ProductDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Product> products);


    @Query("SELECT * FROM product ORDER BY date DESC LIMIT :limit")
    LiveData<List<Product>> getNewProducts(int limit);

    @Query("SELECT * FROM product WHERE onSale = 1 ORDER BY date DESC LIMIT :limit")
    LiveData<List<Product>> getOnSaleProduct(int limit);

    @Query("SELECT * FROM product WHERE featured = 1 ORDER BY date DESC LIMIT :limit")
    LiveData<List<Product>> getFeaturedProduct(int limit);


    @Query("SELECT * FROM product ORDER BY date DESC")
    DataSource.Factory<Integer, Product> getNewProducts();

    @Query("SELECT * FROM product WHERE onSale = 1 ORDER BY date DESC")
    DataSource.Factory<Integer, Product> getOnSaleProduct();

    @Query("SELECT * FROM product WHERE featured = 1 ORDER BY date DESC")
    DataSource.Factory<Integer, Product> getFeaturedProduct();

//    @Query("SELECT * FROM product ORDER BY dateCreated DESC LIMIT :offset , :perPage")
//    List<Product> getProducts(int perPage, int offset, int category);


    @Query("SELECT * FROM product WHERE id = :productId")
    Product getProduct(int productId);

    @Query("SELECT * FROM product WHERE id IN (:ids)")
    LiveData<List<Product>> getRelatedProducts(List<Integer> ids);

    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product INNER JOIN product_category_join " +
            "ON product.id = product_category_join.productId " +
            "WHERE product_category_join.categoryId = :parentId " +
            "ORDER BY cast(price as unsigned) DESC")
    DataSource.Factory<Integer,SimpleProduct> getProductsPriceOrderDESC(Integer parentId);

    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product INNER JOIN product_category_join " +
            "ON product.id = product_category_join.productId " +
            "WHERE product_category_join.categoryId = :parentId " +
            "ORDER BY cast(price as unsigned) ASC")
    DataSource.Factory<Integer,SimpleProduct> getProductsPriceOrderASC(Integer parentId);

    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product INNER JOIN product_category_join " +
            "ON product.id = product_category_join.productId " +
            "WHERE product_category_join.categoryId = :parentId " +
            "ORDER BY cast(sales as unsigned) DESC")
    DataSource.Factory<Integer,SimpleProduct> getProductsBestSellOrder(Integer parentId);

    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product INNER JOIN product_category_join " +
            "ON product.id = product_category_join.productId " +
            "WHERE product_category_join.categoryId = :parentId " +
            "ORDER BY date DESC")
    DataSource.Factory<Integer,SimpleProduct> getProductsDateOrder(Integer parentId);

    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product INNER JOIN product_category_join " +
            "ON product.id = product_category_join.productId " +
            "WHERE product_category_join.categoryId = :parentId " +
            "ORDER BY cast(rating as unsigned) DESC")
    DataSource.Factory<Integer,SimpleProduct> getProductsRatingOrder(Integer parentId);


    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product WHERE description LIKE :query OR name LIKE :query ORDER BY cast(price as unsigned) DESC LIMIT :limit")
    DataSource.Factory<Integer, SimpleProduct> getProductsPriceOrderDESC(String query, int limit);

    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product WHERE description LIKE :query OR name LIKE :query ORDER BY cast(price as unsigned) ASC LIMIT :limit")
    DataSource.Factory<Integer, SimpleProduct> getProductsPriceOrderASC(String query, int limit);

    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product WHERE description LIKE :query OR name LIKE :query ORDER BY cast(sales as unsigned) DESC LIMIT :limit")
    DataSource.Factory<Integer, SimpleProduct> getProductsBestSellOrder(String query, int limit);

    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product WHERE description LIKE :query OR name LIKE :query ORDER BY date DESC LIMIT :limit")
    DataSource.Factory<Integer, SimpleProduct> getProductsDateOrder(String query, int limit);

    @Query("SELECT id, name, featured, price, regularPrice, images, onSale, sales, rating FROM product WHERE description LIKE :query OR name LIKE :query ORDER BY cast(rating as unsigned) DESC LIMIT :limit")
    DataSource.Factory<Integer, SimpleProduct> getProductsRatingOrder(String query, int limit);
}
