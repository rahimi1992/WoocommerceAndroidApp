
package com.test.newshop1.data.database.coupon;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coupon {

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
    private String dateCreated;
    @SerializedName("date_created_gmt")
    @Expose
    private String dateCreatedGmt;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("date_modified_gmt")
    @Expose
    private String dateModifiedGmt;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date_expires")
    @Expose
    private String dateExpires;
    @SerializedName("date_expires_gmt")
    @Expose
    private String dateExpiresGmt;
    @SerializedName("usage_count")
    @Expose
    private Integer usageCount;
    @SerializedName("individual_use")
    @Expose
    private Boolean individualUse;
    @SerializedName("product_ids")
    @Expose
    private List<Integer> productIds = null;
    @SerializedName("excluded_product_ids")
    @Expose
    private List<Integer> excludedProductIds = null;
    @SerializedName("usage_limit")
    @Expose
    private Object usageLimit;
    @SerializedName("usage_limit_per_user")
    @Expose
    private Object usageLimitPerUser;
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
    private List<Integer> excludedProductCategories = null;
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
    @SerializedName("used_by")
    @Expose
    private List<String> usedBy = null;


    @SerializedName("_links")
    @Expose
    private Links links;

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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
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

    public String getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(String dateExpires) {
        this.dateExpires = dateExpires;
    }

    public String getDateExpiresGmt() {
        return dateExpiresGmt;
    }

    public void setDateExpiresGmt(String dateExpiresGmt) {
        this.dateExpiresGmt = dateExpiresGmt;
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

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }

    public List<Integer> getExcludedProductIds() {
        return excludedProductIds;
    }

    public void setExcludedProductIds(List<Integer> excludedProductIds) {
        this.excludedProductIds = excludedProductIds;
    }

    public Object getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Object usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Object getUsageLimitPerUser() {
        return usageLimitPerUser;
    }

    public void setUsageLimitPerUser(Object usageLimitPerUser) {
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

    public List<Integer> getExcludedProductCategories() {
        return excludedProductCategories;
    }

    public void setExcludedProductCategories(List<Integer> excludedProductCategories) {
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

    public List<String> getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(List<String> usedBy) {
        this.usedBy = usedBy;
    }


    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
