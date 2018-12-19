package com.test.newshop1.ui.searchActivity;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.product.Product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private DataRepository mRepository;

    private final LiveData<PagedList<Product>> mProducts = Transformations.switchMap(searchQuery, (query) -> mRepository.searchProducts(query));

    public SearchViewModel(DataRepository repository) {

        mRepository = repository;

    }

    LiveData<PagedList<Product>> getProducts(){
        return mProducts;
    }

    void setSearchQuery(String parentId) {
        this.searchQuery.setValue(parentId);
    }

}
