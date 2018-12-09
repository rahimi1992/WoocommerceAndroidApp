
package com.test.newshop1.data.database.shipping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingMethod {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("instance_id")
    @Expose
    private Integer instanceId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("method_id")
    @Expose
    private String methodId;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("method_description")
    @Expose
    private String methodDescription;
    @SerializedName("settings")
    @Expose
    private Settings settings;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getMethodTitle() {
        return methodTitle;
    }

    public void setMethodTitle(String methodTitle) {
        this.methodTitle = methodTitle;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getCostValue() {
        if (settings.hasCost())
            return settings.getCost().getValue();
        else
            return "0";
    }
}
