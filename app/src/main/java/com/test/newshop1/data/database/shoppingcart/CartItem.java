package com.test.newshop1.data.database.shoppingcart;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.newshop1.data.database.product.SimpleCategory;

import java.util.List;

@Entity(tableName = "shopping_cart")
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String name;

    private Integer productId;

    private Integer variationId;

    private Integer quantity;

    private String price;

    private String imageSrc;

    @TypeConverters(CategoryConverter.class)
    private List<SimpleCategory> categories;



    public CartItem(Integer id, String name, Integer productId, Integer variationId, Integer quantity, String price, String imageSrc, List<SimpleCategory> categories) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
        this.price = price;
        this.imageSrc = imageSrc;
        this.categories = categories;
    }

    @Ignore
    public CartItem(String name, Integer productId, Integer variationId, Integer quantity, String price, String imageSrc, List<SimpleCategory> categories) {
        this.name = name;
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
        this.price = price;
        this.imageSrc = imageSrc;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public List<SimpleCategory> getCategories() {
        return categories;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public CartItem cloneItem() {
        return new CartItem(name, productId, variationId, quantity, price, imageSrc, categories);
    }
}
