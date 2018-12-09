
package com.test.newshop1.data.database.order;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponLine {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discount_tax")
    @Expose
    private String discountTax;
    @SerializedName("meta_data")
    @Expose
    private List<MetaData> metaData = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountTax() {
        return discountTax;
    }

    public void setDiscountTax(String discountTax) {
        this.discountTax = discountTax;
    }

    public List<MetaData> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaData> metaData) {
        this.metaData = metaData;
    }

}
