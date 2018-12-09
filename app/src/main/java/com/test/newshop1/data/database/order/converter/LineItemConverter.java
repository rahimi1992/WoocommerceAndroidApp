package com.test.newshop1.data.database.order.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.newshop1.data.database.order.LineItem;
import com.test.newshop1.data.database.product.Image;

import java.lang.reflect.Type;
import java.util.List;

public class LineItemConverter {

    @TypeConverter
    public String toString(List<LineItem> items){
        Gson gson = new Gson();
        return gson.toJson(items);
    }

    @TypeConverter
    public List<LineItem> fromString(String items){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<LineItem>>() {}.getType();
        return gson.fromJson(items, listType);
    }

}
