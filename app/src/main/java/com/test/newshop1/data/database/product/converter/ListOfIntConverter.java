package com.test.newshop1.data.database.product.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListOfIntConverter {
    @TypeConverter
    public String toString(List<Integer> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public List<Integer> fromString(String list){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(list, listType);
    }
}
