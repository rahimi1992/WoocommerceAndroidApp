package com.test.newshop1.ui.ordersActivity;

import android.util.Log;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.order.Order;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class OrdersViewModel extends ViewModel {
    private static final String TAG = "OrdersViewModel";

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


    public void updateOrder(String orderId){
        dataRepository.updateOrder(orderId, new Order(true));
    }

}
