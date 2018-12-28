package com.test.newshop1.data;

import java.io.Serializable;

public class ProductListOptions implements Serializable {
    private Integer parentId;
    private Boolean featured;
    private Boolean onSale;
    private String searchQuery;
    private OrderBy orderBy;
    private Integer limit;

//    public ProductListOptions(int parentId, String searchQuery, OrderBy orderBy) {
//        this.parentId = parentId;
//        this.searchQuery = searchQuery;
//        this.orderBy = orderBy;
//    }


    public ProductListOptions() {
        orderBy = OrderBy.DATE;
    }

    public Integer getParentId() {
        return parentId;
    }

    public ProductListOptions setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public ProductListOptions setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public ProductListOptions setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public ProductListOptions setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public ProductListOptions setFeatured(Boolean featured) {
        this.featured = featured;
        return this;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public ProductListOptions setOnSale(Boolean onSale) {
        this.onSale = onSale;
        return this;
    }

    public boolean hasParentId(){
        return parentId != null;
    }

    public boolean hasFeatured(){
        return featured != null;
    }

    public boolean hasOnSale(){
        return onSale != null;
    }

    public boolean hasSearchQuery(){
        return searchQuery != null;
    }

    public boolean hasOrderBy(){
        return orderBy != null;
    }

    public boolean hasLimit(){
        return limit != null;
    }
}
