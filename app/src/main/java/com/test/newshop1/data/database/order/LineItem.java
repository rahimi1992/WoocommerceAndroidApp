
package com.test.newshop1.data.database.order;

import android.arch.persistence.room.Entity;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.newshop1.data.database.product.MetaDatum;


@Entity(tableName = "shopping_cart")
public class LineItem {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("product_id")
    @Expose
    private Integer productId;

    @SerializedName("variation_id")
    @Expose
    private Integer variationId;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("tax_class")
    @Expose
    private String taxClass;

    @SerializedName("subtotal")
    @Expose
    private String subtotal;

    @SerializedName("subtotal_tax")
    @Expose
    private String subtotalTax;

    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("total_tax")
    @Expose
    private String totalTax;

    @SerializedName("taxes")
    @Expose
    private List<Object> taxes = null;

    @SerializedName("meta_data")
    @Expose
    private List<MetaDatum> metaData = null;

    @SerializedName("sku")
    @Expose
    private String sku;

    @SerializedName("price")
    @Expose
    private Float price;

    public LineItem(Integer id, String name, Integer productId, Integer variationId, Integer quantity) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
