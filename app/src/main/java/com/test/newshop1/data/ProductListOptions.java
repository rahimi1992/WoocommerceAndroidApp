package com.test.newshop1.data;

import java.io.Serializable;

public class ProductListOptions implements Serializable {
    private Integer parentId;
    private Boolean featured;
    private Boolean onSale;
    private String searchQuery;
    private OrderBy orderBy;

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

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
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
}
