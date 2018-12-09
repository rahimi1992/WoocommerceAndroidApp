package com.test.newshop1.data.database.product.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.newshop1.data.database.product.Image;
import com.test.newshop1.data.database.product.SimpleCategory;

import java.lang.reflect.Type;
import java.util.List;

public class SimpleCategoryConverter {

    @TypeConverter
    public String toString(List<SimpleCategory> cats){
        Gson gson = new Gson();
        return gson.toJson(cats);
    }

    @TypeConverter
    public List<SimpleCategory> fromString(String cats){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<SimpleCategory>>() {}.getType();
        return gson.fromJson(cats, listType);
    }

}
