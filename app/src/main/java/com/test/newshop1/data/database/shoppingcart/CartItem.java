package com.test.newshop1.data.database.shoppingcart;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.test.newshop1.data.database.product.SimpleCategory;

import java.util.List;

@Entity(tableName = "shopping_cart")
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("product_id")
    private Integer productId;

    @SerializedName("variation_id")
    private Integer variationId;

    @SerializedName("quantity")
    private Integer quantity;

    @SerializedName("total")
    private String total;

    @SerializedName("subtotal")
    private String subtotal;


    private String imageSrc;

    @TypeConverters(CategoryConverter.class)
    private List<SimpleCategory> categories;



    public CartItem(Integer id, String name, Integer productId, Integer variationId, Integer quantity, String total, String subtotal, String imageSrc, List<SimpleCategory> categories) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
        this.total = total;
        this.subtotal = subtotal;
        this.imageSrc = imageSrc;
        this.categories = categories;
    }

    @Ignore
    public CartItem(String name, Integer productId, Integer variationId, Integer quantity, String subtotal, String imageSrc, List<SimpleCategory> categories) {
        this.name = name;
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
        this.total = subtotal;
        this.subtotal = subtotal;
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

    public String getTotal() {
        return total;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public List<SimpleCategory> getCategories() {
        return categories;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public CartItem cloneItem() {
        return new CartItem(name, productId, variationId, quantity, subtotal, imageSrc, categories);
    }

    public void resetDiscount(){
        this.total = String.valueOf(this.subtotal);
    }
}
