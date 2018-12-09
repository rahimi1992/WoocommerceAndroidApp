
package com.test.newshop1.data.database.product;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "simple_category", indices = {@Index(value = {"id"})})
public class SimpleCategory implements Serializable {

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


    public SimpleCategory(Integer id, String name, String slug) {

        this.id = id;
        this.name = name;
        this.slug = slug;
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


}
