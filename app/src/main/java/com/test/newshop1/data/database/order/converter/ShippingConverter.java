package com.test.newshop1.data.database.order.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.test.newshop1.data.database.customer.Shipping;

public class ShippingConverter {

    @TypeConverter
    public String toString(Shipping shipping){
        Gson gson = new Gson();
        return gson.toJson(shipping);
    }

    @TypeConverter
    public Shipping fromString(String shipping){
        Gson gson = new Gson();
        return gson.fromJson(shipping, Shipping.class);
    }

}
