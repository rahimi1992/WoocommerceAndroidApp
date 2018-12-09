package com.test.newshop1.data.database.product;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "product_category_join",
        primaryKeys = {"productId", "categoryId"},
        foreignKeys = {@ForeignKey(entity = Product.class,
                                   parentColumns = "id",
                                   childColumns = "productId"),
                       @ForeignKey(entity = SimpleCategory.class,
                                   parentColumns = "id",
                                   childColumns = "categoryId")})
public class ProductCategoryJoin {

    private final int productId;
    private final int categoryId;

    public ProductCategoryJoin(int productId, int categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
