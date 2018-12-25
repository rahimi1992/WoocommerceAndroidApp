package com.test.newshop1.ui.homeActivity;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.product.Product;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private DataRepository dataRepository;

    public HomeViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;

    }


    public LiveData<List<Product>> getNewProducts() {
        return dataRepository.getNewProducts(20);
    }

    public LiveData<List<Product>> getFeaturedProducts() {
        return dataRepository.getFeaturedProducts(20);
    }

    public LiveData<List<Product>> getOnSaleProducts() {
        return dataRepository.getOnSaleProducts(20);
    }


}
