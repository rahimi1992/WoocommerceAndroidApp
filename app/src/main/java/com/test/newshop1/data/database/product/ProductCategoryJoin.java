package com.test.newshop1.data.database.product;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "product_category_join",
        indices = {@Index(value = {"categoryId"})},
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
