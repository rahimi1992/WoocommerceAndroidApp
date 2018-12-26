package com.test.newshop1.ui.productListActivity;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.OrderBy;
import com.test.newshop1.data.ProductListOptions;
import com.test.newshop1.data.database.product.Product;


public class ProductListActivityViewModel extends ViewModel {

    private MutableLiveData<ProductListOptions> options = new MutableLiveData<>();

    private DataRepository mRepository;

    private final LiveData<PagedList<Product>> mProducts = Transformations.switchMap(options, (opt) -> mRepository.getProducts(opt));
    public ProductListActivityViewModel(DataRepository repository) {

        mRepository = repository;
        options.setValue(new ProductListOptions());

    }

    LiveData<PagedList<Product>> getProducts(){
        return mProducts;
    }


    void setParentId(int parentId) {
        if (options.getValue() != null)
            options.getValue().setParentId(parentId);
    }


    void setOrderBy(OrderBy orderBy){
        if (options.getValue() != null)
            options.getValue().setOrderBy(orderBy);

    }

    LiveData<Integer> getCartItemCount() {
        return mRepository.getCartItemCount();
    }

}
