
package com.test.newshop1.data.database.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "category" , indices = {@Index(value = {"id"})})
public class Category {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("parent")
    @Expose
    private Integer parent;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("display")
    @Expose
    private String display;

    @Embedded
    @SerializedName("image")
    @Expose
    private Image image;

    @SerializedName("menu_order")
    @Expose
    private Integer menuOrder;
    @SerializedName("count")
    @Expose
    private Integer count;

    @Ignore
    public ObservableBoolean isSelected = new ObservableBoolean(false);

    @Ignore
    @SerializedName("_links")
    @Expose
    private Links links;

    @Ignore
    private List<String> subCatTitles;

    public Category(Integer id, String name, String slug, Integer parent, String description, String display, Image image, Integer menuOrder, Integer count) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.parent = parent;
        this.description = description;
        this.display = display;
        this.image = image;
        this.menuOrder = menuOrder;
        this.count = count;
    }

    @Ignore
    public Category(Integer id, Integer parent) {
        this.id = id;
        this.parent = parent;
    }

    public void setSelected(boolean selected) {
        isSelected.set(selected);
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

    public Integer getParent() {
        return parent;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplay() {
        return display;
    }

    public Image getImage() {
        return image;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public Integer getCount() {
        return count;
    }


    public List<String> getSubCatTitles() {
        return subCatTitles;
    }

    public void setSubCatTitles(List<String> subCatTitles) {
        this.subCatTitles = subCatTitles;
    }
}
