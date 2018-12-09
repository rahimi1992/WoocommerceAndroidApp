
package com.test.newshop1.data.database.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    @Expose
    private Integer imageId;
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
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("alt")
    @Expose
    private String alt;

    public Image(Integer imageId, String dateCreated, String dateCreatedGmt, String dateModified, String dateModifiedGmt, String src, String title, String alt) {
        this.imageId = imageId;
        this.dateCreated = dateCreated;
        this.dateCreatedGmt = dateCreatedGmt;
        this.dateModified = dateModified;
        this.dateModifiedGmt = dateModifiedGmt;
        this.src = src;
        this.title = title;
        this.alt = alt;
    }

    public Integer getImageId() {
        return imageId;
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

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public String getSrc() {
        return src;
    }

    public String getTitle() {
        return title;
    }

    public String getAlt() {
        return alt;
    }
}
