package com.test.newshop1.data.database.order.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.test.newshop1.data.database.customer.Billing;

public class BillingConverter {

    @TypeConverter
    public String toString(Billing billing){
        Gson gson = new Gson();
        return gson.toJson(billing);
    }

    @TypeConverter
    public Billing fromString(String billing){
        Gson gson = new Gson();
        return gson.fromJson(billing, Billing.class);
    }

}
