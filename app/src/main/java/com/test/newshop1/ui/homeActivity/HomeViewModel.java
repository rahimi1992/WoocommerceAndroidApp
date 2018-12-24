package com.test.newshop1.ui.homeActivity;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.product.Product;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class HomeViewModel extends ViewModel {

    private DataRepository dataRepository;

    private MutableLiveData<Integer> parent1 = new MutableLiveData<>();
    private MutableLiveData<Integer> parent2 = new MutableLiveData<>();

    private final LiveData<PagedList<Product>> products1 =
            Transformations.switchMap(parent1, (id) -> dataRepository.getProducts(id));

    private final LiveData<PagedList<Product>> products2 =
            Transformations.switchMap(parent2, (id) -> dataRepository.getProducts(id));

    public HomeViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;

    }

    public void setParent1(Integer parent) {
        this.parent1.setValue(parent);
    }


    public void setParent2(Integer parent) {
        this.parent2.setValue(parent);
    }

    public LiveData<PagedList<Product>> getProducts1() {
        return products1;
    }

    public LiveData<PagedList<Product>> getProducts2() {
        return products2;
    }
}
