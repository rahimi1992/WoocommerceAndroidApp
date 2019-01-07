package com.test.newshop1.ui.searchActivity;

import android.util.Log;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.OrderBy;
import com.test.newshop1.data.ProductListOptions;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.data.database.product.SimpleProduct;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class SearchViewModel extends ViewModel {
    private static final String TAG = "SearchViewModel";

    private MutableLiveData<ProductListOptions> options = new MutableLiveData<>();
    private DataRepository mRepository;

    private final LiveData<PagedList<SimpleProduct>> mProducts = Transformations.switchMap(options, (options) -> mRepository.getProducts(options));

    public SearchViewModel(DataRepository repository) {

        mRepository = repository;
        options.setValue(new ProductListOptions());
    }

    LiveData<PagedList<SimpleProduct>> getProducts(){
        return mProducts;
    }

    void setSearchQuery(String query) {
        if (options.getValue() != null) {
            Log.d(TAG, "setSearchQuery: " + query);
            options.setValue(options.getValue().setSearchQuery(query));
        }
    }

    void setOrderBy(OrderBy orderBy) {
        if (options.getValue() != null)
            options.setValue(options.getValue().setOrderBy(orderBy));
    }
}
