
package com.test.newshop1.data.database.shipping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Options {

    @SerializedName("coupon")
    @Expose
    private String coupon;
    @SerializedName("min_amount")
    @Expose
    private String minAmount;
    @SerializedName("either")
    @Expose
    private String either;
    @SerializedName("both")
    @Expose
    private String both;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getEither() {
        return either;
    }

    public void setEither(String either) {
        this.either = either;
    }

    public String getBoth() {
        return both;
    }

    public void setBoth(String both) {
        this.both = both;
    }

}
