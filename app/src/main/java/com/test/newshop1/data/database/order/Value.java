
package com.test.newshop1.data.database.order;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("date_created")
    @Expose
    private DateCreated dateCreated;
    @SerializedName("date_modified")
    @Expose
    private DateModified dateModified;
    @SerializedName("date_expires")
    @Expose
    private DateExpires dateExpires;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("usage_count")
    @Expose
    private Integer usageCount;
    @SerializedName("individual_use")
    @Expose
    private Boolean individualUse;
    @SerializedName("product_ids")
    @Expose
    private List<Object> productIds = null;
    @SerializedName("excluded_product_ids")
    @Expose
    private List<Object> excludedProductIds = null;
    @SerializedName("usage_limit")
    @Expose
    private Integer usageLimit;
    @SerializedName("usage_limit_per_user")
    @Expose
    private Integer usageLimitPerUser;
    @SerializedName("limit_usage_to_x_items")
    @Expose
    private Object limitUsageToXItems;
    @SerializedName("free_shipping")
    @Expose
    private Boolean freeShipping;
    @SerializedName("product_categories")
    @Expose
    private List<Integer> productCategories = null;
    @SerializedName("excluded_product_categories")
    @Expose
    private List<Object> excludedProductCategories = null;
    @SerializedName("exclude_sale_items")
    @Expose
    private Boolean excludeSaleItems;
    @SerializedName("minimum_amount")
    @Expose
    private String minimumAmount;
    @SerializedName("maximum_amount")
    @Expose
    private String maximumAmount;
    @SerializedName("email_restrictions")
    @Expose
    private List<Object> emailRestrictions = null;
    @SerializedName("virtual")
    @Expose
    private Boolean virtual;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public DateCreated getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateCreated dateCreated) {
        this.dateCreated = dateCreated;
    }

    public DateModified getDateModified() {
        return dateModified;
    }

    public void setDateModified(DateModified dateModified) {
        this.dateModified = dateModified;
    }

    public DateExpires getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(DateExpires dateExpires) {
        this.dateExpires = dateExpires;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public Boolean getIndividualUse() {
        return individualUse;
    }

    public void setIndividualUse(Boolean individualUse) {
        this.individualUse = individualUse;
    }

    public List<Object> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Object> productIds) {
        this.productIds = productIds;
    }

    public List<Object> getExcludedProductIds() {
        return excludedProductIds;
    }

    public void setExcludedProductIds(List<Object> excludedProductIds) {
        this.excludedProductIds = excludedProductIds;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getUsageLimitPerUser() {
        return usageLimitPerUser;
    }

    public void setUsageLimitPerUser(Integer usageLimitPerUser) {
        this.usageLimitPerUser = usageLimitPerUser;
    }

    public Object getLimitUsageToXItems() {
        return limitUsageToXItems;
    }

    public void setLimitUsageToXItems(Object limitUsageToXItems) {
        this.limitUsageToXItems = limitUsageToXItems;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public List<Integer> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<Integer> productCategories) {
        this.productCategories = productCategories;
    }

    public List<Object> getExcludedProductCategories() {
        return excludedProductCategories;
    }

    public void setExcludedProductCategories(List<Object> excludedProductCategories) {
        this.excludedProductCategories = excludedProductCategories;
    }

    public Boolean getExcludeSaleItems() {
        return excludeSaleItems;
    }

    public void setExcludeSaleItems(Boolean excludeSaleItems) {
        this.excludeSaleItems = excludeSaleItems;
    }

    public String getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public String getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public List<Object> getEmailRestrictions() {
        return emailRestrictions;
    }

    public void setEmailRestrictions(List<Object> emailRestrictions) {
        this.emailRestrictions = emailRestrictions;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public List<MetaData> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaData> metaData) {
        this.metaData = metaData;
    }

}
