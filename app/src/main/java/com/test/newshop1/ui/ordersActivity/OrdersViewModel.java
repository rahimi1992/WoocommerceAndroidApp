package com.test.newshop1.ui.ordersActivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.order.Order;

import java.util.List;

public class OrdersViewModel extends ViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<Integer> customerId = new MutableLiveData<>();
    private final LiveData<List<Order>> orders =
            Transformations.switchMap(customerId, (id) -> dataRepository.getOrders(id));

    public OrdersViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;

        dataRepository.getLoggedInCustomer().observeForever(customer -> {
            if (customer != null){
                customerId.setValue(customer.getId());
            }
        });
    }

    LiveData<List<Order>> getOrders() {
        return orders;
    }

}
