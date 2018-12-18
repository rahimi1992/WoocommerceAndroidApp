package com.test.newshop1.ui.ordersActivity;

import android.arch.lifecycle.ViewModel;

import com.test.newshop1.data.DataRepository;

public class OrdersViewModel extends ViewModel {
    private DataRepository dataRepository;

    public OrdersViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
}
