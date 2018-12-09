package com.test.newshop1.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.customer.Customer;

public class BaseActivityViewModel extends ViewModel {

    private DataRepository dataRepository;


    public BaseActivityViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public LiveData<Customer> getLoggedInCustomer(){
        return dataRepository.getLoggedInCustomer();
    }


    public void logout(int id){
        dataRepository.logoutCustomer(id);
    }

}
