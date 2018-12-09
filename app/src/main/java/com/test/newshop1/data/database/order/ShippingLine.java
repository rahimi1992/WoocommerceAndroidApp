
package com.test.newshop1.data.database.order;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.newshop1.data.database.product.MetaDatum;

public class ShippingLine {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("method_id")
    @Expose
    private String methodId;
    @SerializedName("instance_id")
    @Expose
    private String instanceId;
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
    private List<MetaData> metaData = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethodTitle() {
        return methodTitle;
    }

    public void setMethodTitle(String methodTitle) {
        this.methodTitle = methodTitle;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
        this.taxes = taxes;
    }

    public List<MetaData> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaData> metaData) {
        this.metaData = metaData;
    }

}
