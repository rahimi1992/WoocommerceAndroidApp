package com.test.newshop1.ui.ordersActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

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
