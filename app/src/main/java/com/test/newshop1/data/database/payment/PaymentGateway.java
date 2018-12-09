
package com.test.newshop1.data.database.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentGateway {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
