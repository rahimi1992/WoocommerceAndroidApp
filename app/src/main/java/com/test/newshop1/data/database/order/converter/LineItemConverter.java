package com.test.newshop1.data.database.order.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.newshop1.data.database.shoppingcart.CartItem;

import java.lang.reflect.Type;
import java.util.List;

public class LineItemConverter {

    @TypeConverter
    public String toString(List<CartItem> items){
        Gson gson = new Gson();
        return gson.toJson(items);
    }

    @TypeConverter
    public List<CartItem> fromString(String items){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CartItem>>() {}.getType();
        return gson.fromJson(items, listType);
    }

}
