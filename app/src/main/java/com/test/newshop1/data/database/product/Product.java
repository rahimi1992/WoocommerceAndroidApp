
package com.test.newshop1.data.database.product;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.newshop1.data.database.product.converter.ImageConverter;
import com.test.newshop1.data.database.product.converter.ListOfIntConverter;
import com.test.newshop1.data.database.product.converter.SimpleCategoryConverter;

import java.io.Serializable;
import java.util.List;


@Entity(tableName = "product", indices = {@Index(value = {"id"}, unique = true)})
public class Product implements Serializable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("permalink")
    @Expose
    private String permalink;
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
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("featured")
    @Expose
    private Boolean featured;
    @SerializedName("catalog_visibility")
    @Expose
    private String catalogVisibility;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("regular_price")
    @Expose
    private String regularPrice;
    @SerializedName("sale_price")
    @Expose
    private String salePrice;

    @SerializedName("images")
    @Expose
    @TypeConverters(ImageConverter.class)
    private List<Image> images = null;

    @SerializedName("on_sale")
    @Expose
    private Boolean onSale;

//    @Ignore
//    private final String thumbSrc;
//
//    public String getThumbSrc() {
//        return images.get(0).getSrc();
//    }


    @SerializedName("related_ids")
    @Expose
    @TypeConverters(ListOfIntConverter.class)
    private List<Integer> relatedIds = null;


    @SerializedName("categories")
    @Expose
    @TypeConverters(SimpleCategoryConverter.class)
    private List<SimpleCategory> categories = null;


    @SerializedName("date_on_sale_from")
    @Expose
    @Ignore
    private Object dateOnSaleFrom;
    @SerializedName("date_on_sale_from_gmt")
    @Expose
    @Ignore
    private Object dateOnSaleFromGmt;
    @SerializedName("date_on_sale_to")
    @Expose
    @Ignore
    private Object dateOnSaleTo;
    @SerializedName("date_on_sale_to_gmt")
    @Expose
    @Ignore
    private Object dateOnSaleToGmt;
    @SerializedName("price_html")
    @Expose
    @Ignore
    private String priceHtml;

    @SerializedName("purchasable")
    @Expose
    @Ignore
    private Boolean purchasable;
    @SerializedName("total_sales")
    @Expose
    @Ignore
    private Integer totalSales;
    @SerializedName("virtual")
    @Expose
    @Ignore
    private Boolean virtual;
    @SerializedName("downloadable")
    @Expose
    @Ignore
    private Boolean downloadable;
    @SerializedName("downloads")
    @Expose
    @Ignore
    private List<Object> downloads = null;
    @SerializedName("download_limit")
    @Expose
    @Ignore
    private Integer downloadLimit;
    @SerializedName("download_expiry")
    @Expose
    @Ignore
    private Integer downloadExpiry;
    @SerializedName("external_url")
    @Expose
    @Ignore
    private String externalUrl;
    @SerializedName("button_text")
    @Expose
    @Ignore
    private String buttonText;
    @SerializedName("tax_status")
    @Expose
    @Ignore
    private String taxStatus;
    @SerializedName("tax_class")
    @Expose
    @Ignore
    private String taxClass;
    @SerializedName("manage_stock")
    @Expose
    @Ignore
    private Boolean manageStock;
    @SerializedName("stock_quantity")
    @Expose
    @Ignore
    private Object stockQuantity;
    @SerializedName("in_stock")
    @Expose
    @Ignore
    private Boolean inStock;
    @SerializedName("backorders")
    @Expose
    @Ignore
    private String backorders;
    @SerializedName("backorders_allowed")
    @Expose
    @Ignore
    private Boolean backordersAllowed;
    @SerializedName("backordered")
    @Expose
    @Ignore
    private Boolean backordered;
    @SerializedName("sold_individually")
    @Expose
    @Ignore
    private Boolean soldIndividually;
    @SerializedName("weight")
    @Expose
    @Ignore
    private String weight;
    @SerializedName("dimensions")
    @Expose
    @Ignore
    private Dimensions dimensions;
    @SerializedName("shipping_required")
    @Expose
    @Ignore
    private Boolean shippingRequired;
    @SerializedName("shipping_taxable")
    @Expose
    @Ignore
    private Boolean shippingTaxable;
    @SerializedName("shipping_class")
    @Expose
    @Ignore
    private String shippingClass;
    @SerializedName("shipping_class_id")
    @Expose
    @Ignore
    private Integer shippingClassId;
    @SerializedName("reviews_allowed")
    @Expose
    @Ignore
    private Boolean reviewsAllowed;
    @SerializedName("average_rating")
    @Expose
    @Ignore
    private String averageRating;
    @SerializedName("rating_count")
    @Expose
    @Ignore
    private Integer ratingCount;

    @SerializedName("upsell_ids")
    @Expose
    @Ignore
    private List<Object> upsellIds = null;
    @SerializedName("cross_sell_ids")
    @Expose
    @Ignore
    private List<Object> crossSellIds = null;
    @SerializedName("parent_id")
    @Expose
    @Ignore
    private Integer parentId;
    @SerializedName("purchase_note")
    @Expose
    @Ignore
    private String purchaseNote;

    @SerializedName("tags")
    @Expose
    @Ignore
    private List<Tag> tags = null;


    @SerializedName("attributes")
    @Expose
    @Ignore
    private List<Attribute> attributes = null;
    @SerializedName("default_attributes")
    @Expose
    @Ignore
    private List<Object> defaultAttributes = null;
    @SerializedName("variations")
    @Expose
    @Ignore
    private List<Object> variations = null;
    @SerializedName("grouped_products")
    @Expose
    @Ignore
    private List<Object> groupedProducts = null;
    @SerializedName("menu_order")
    @Expose
    @Ignore
    private Integer menuOrder;
    @SerializedName("meta_data")
    @Expose
    @Ignore
    private List<MetaDatum> metaData = null;
    @SerializedName("brands")
    @Expose
    @Ignore
    private List<Brand> brands = null;
    @SerializedName("_links")
    @Expose
    @Ignore
    private Links links;


    public Product(Integer id, String name, String slug, String permalink, String dateCreated,
                   String dateCreatedGmt, String dateModified, String dateModifiedGmt, String type,
                   String status, Boolean featured, String catalogVisibility, String description,
                   String shortDescription, String sku, String price, String regularPrice, String salePrice,
                   List<Image> images, List<Integer> relatedIds, List<SimpleCategory> categories, Boolean onSale) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.permalink = permalink;
        this.dateCreated = dateCreated;
        this.dateCreatedGmt = dateCreatedGmt;
        this.dateModified = dateModified;
        this.dateModifiedGmt = dateModifiedGmt;
        this.type = type;
        this.status = status;
        this.featured = featured;
        this.catalogVisibility = catalogVisibility;
        this.description = description;
        this.shortDescription = shortDescription;
        this.sku = sku;
        this.price = price;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
        this.images = images;
        this.relatedIds = relatedIds;
        this.categories = categories;
        this.onSale = onSale;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
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

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public String getCatalogVisibility() {
        return catalogVisibility;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getSku() {
        return sku;
    }

    public String getPrice() {
        return price;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public List<SimpleCategory> getCategories() {
        return categories;
    }

    public Object getDateOnSaleFrom() {
        return dateOnSaleFrom;
    }

    public Object getDateOnSaleFromGmt() {
        return dateOnSaleFromGmt;
    }

    public Object getDateOnSaleTo() {
        return dateOnSaleTo;
    }

    public Object getDateOnSaleToGmt() {
        return dateOnSaleToGmt;
    }

    public String getPriceHtml() {
        return priceHtml;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public Boolean getPurchasable() {
        return purchasable;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public List<Object> getDownloads() {
        return downloads;
    }

    public Integer getDownloadLimit() {
        return downloadLimit;
    }

    public Integer getDownloadExpiry() {
        return downloadExpiry;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public Boolean getManageStock() {
        return manageStock;
    }

    public Object getStockQuantity() {
        return stockQuantity;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public String getBackorders() {
        return backorders;
    }

    public Boolean getBackordersAllowed() {
        return backordersAllowed;
    }

    public Boolean getBackordered() {
        return backordered;
    }

    public Boolean getSoldIndividually() {
        return soldIndividually;
    }

    public String getWeight() {
        return weight;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public Boolean getShippingRequired() {
        return shippingRequired;
    }

    public Boolean getShippingTaxable() {
        return shippingTaxable;
    }

    public String getShippingClass() {
        return shippingClass;
    }

    public Integer getShippingClassId() {
        return shippingClassId;
    }

    public Boolean getReviewsAllowed() {
        return reviewsAllowed;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public List<Integer> getRelatedIds() {
        return relatedIds;
    }

    public List<Object> getUpsellIds() {
        return upsellIds;
    }

    public List<Object> getCrossSellIds() {
        return crossSellIds;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getPurchaseNote() {
        return purchaseNote;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Object> getDefaultAttributes() {
        return defaultAttributes;
    }

    public List<Object> getVariations() {
        return variations;
    }

    public List<Object> getGroupedProducts() {
        return groupedProducts;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public List<MetaDatum> getMetaData() {
        return metaData;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public Links getLinks() {
        return links;
    }

    @Override
    public String toString() {
        return "{\"Product\":{"
                + "\"id\":\"" + id + "\""
                + "}}";
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o instanceof Product){
            Product p = (Product) o;
            return  p.getId().equals(id) &&
                    p.getName().equals(name) &&
                    p.getPrice().equals(price) &&
                    p.getRegularPrice().equals(regularPrice);
        } else
            return false;

    }
}
