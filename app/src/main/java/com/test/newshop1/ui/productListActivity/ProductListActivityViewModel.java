package com.test.newshop1.ui.productListActivity;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.product.Product;


public class ProductListActivityViewModel extends ViewModel {

    private MutableLiveData<Integer> parentId = new MutableLiveData<>();
    private DataRepository mRepository;

    private final LiveData<PagedList<Product>> mProducts = Transformations.switchMap(parentId, (parent) -> mRepository.getProducts(parent));

    public ProductListActivityViewModel(DataRepository repository) {

        mRepository = repository;

    }

    LiveData<PagedList<Product>> getProducts(){
        return mProducts;
    }

    void setParentId(int parentId) {
        this.parentId.setValue(parentId);
    }


    LiveData<Integer> getCartItemCount() {
        return mRepository.getCartItemCount();
    }

}
