
package com.test.newshop1.data.database.shipping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Options_ {

    @SerializedName("taxable")
    @Expose
    private String taxable;
    @SerializedName("none")
    @Expose
    private String none;

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getNone() {
        return none;
    }

    public void setNone(String none) {
        this.none = none;
    }

}
