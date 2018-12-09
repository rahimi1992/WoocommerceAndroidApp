package com.test.newshop1.data.database.shoppingcart;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetItem(CartItem item);

    @Query("SELECT * FROM shopping_cart")
    LiveData<List<CartItem>> getCartItems();

    @Query("DELETE FROM shopping_cart WHERE id = :itemId")
    void removeItem(int itemId);

    @Query("UPDATE shopping_cart SET quantity = quantity + 1 WHERE id = :itemId")
    void increaseItem(int itemId);

    @Query("UPDATE shopping_cart SET quantity = quantity - 1 WHERE id = :itemId AND quantity>1")
    void decreaseItem(int itemId);

    @Query("SELECT SUM(quantity) FROM shopping_cart")
    LiveData<Integer> getCartItemCount();

    @Query("SELECT * FROM shopping_cart WHERE productId = :productId AND variationId = :variationId")
    CartItem getCartItemByPId(int productId, int variationId);

    @Query("SELECT SUM(quantity*total) from shopping_cart")
    LiveData<Integer> getTotalPrice();
}
