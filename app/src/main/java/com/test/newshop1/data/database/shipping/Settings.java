
package com.test.newshop1.data.database.shipping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {

    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("requires")
    @Expose
    private Requires requires;
    @SerializedName("min_amount")
    @Expose
    private MinAmount minAmount;
    @SerializedName("tax_status")
    @Expose
    private TaxStatus taxStatus;
    @SerializedName("cost")
    @Expose
    private Cost cost;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Requires getRequires() {
        return requires;
    }

    public void setRequires(Requires requires) {
        this.requires = requires;
    }

    public MinAmount getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(MinAmount minAmount) {
        this.minAmount = minAmount;
    }

    public TaxStatus getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(TaxStatus taxStatus) {
        this.taxStatus = taxStatus;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public boolean hasCost() {
        return cost != null;
    }
}
