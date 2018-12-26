package com.test.newshop1.ui.searchActivity;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.ProductListOptions;
import com.test.newshop1.data.database.product.Product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<ProductListOptions> options = new MutableLiveData<>();
    private DataRepository mRepository;

    private final LiveData<PagedList<Product>> mProducts = Transformations.switchMap(options, (options) -> mRepository.getProducts(options));

    public SearchViewModel(DataRepository repository) {

        mRepository = repository;
        options.setValue(new ProductListOptions());
    }

    LiveData<PagedList<Product>> getProducts(){
        return mProducts;
    }

    void setSearchQuery(String query) {
        if (options.getValue() != null)
            options.getValue().setSearchQuery(query);
    }

}
