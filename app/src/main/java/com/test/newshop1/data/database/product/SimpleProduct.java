
package com.test.newshop1.data.database.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.newshop1.data.database.product.converter.ImageConverter;
import com.test.newshop1.data.database.product.converter.ListOfIntConverter;
import com.test.newshop1.data.database.product.converter.SimpleCategoryConverter;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

public class SimpleProduct implements Serializable {

    private Integer id;

    private String name;

    private Boolean featured;

    private String price;

    private String regularPrice;

    @TypeConverters(ImageConverter.class)
    private List<Image> images = null;

    private Boolean onSale;

    @ColumnInfo(name = "sales")
    @Expose
    private Integer totalSales;

    @ColumnInfo(name = "rating")
    private String averageRating;



    public SimpleProduct(Integer id, String name, Boolean featured, String price, String regularPrice,
                         List<Image> images, Boolean onSale, Integer totalSales, String averageRating) {
        this.id = id;
        this.name = name;
        this.featured = featured;
        this.price = price;
        this.regularPrice = regularPrice;
        this.images = images;
        this.onSale = onSale;
        this.totalSales = totalSales;
        this.averageRating = averageRating;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public String getPrice() {
        return price;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public List<Image> getImages() {
        return images;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o instanceof SimpleProduct){
            SimpleProduct p = (SimpleProduct) o;
            return  p.getId().equals(id) &&
                    p.getName().equals(name) &&
                    p.getPrice().equals(price) &&
                    p.getRegularPrice().equals(regularPrice);
        } else
            return false;

    }
}
