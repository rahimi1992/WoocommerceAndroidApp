package com.test.newshop1.ui.homeActivity;

import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.OrderBy;
import com.test.newshop1.data.ProductListOptions;
import com.test.newshop1.data.ResponseCallback;
import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.data.database.product.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class HomeViewModel extends ViewModel {

    private DataRepository dataRepository;

    public HomeViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        loadCats();



        initOptions();

    }

    private void initOptions() {
        options1.setValue(new ProductListOptions().setOrderBy(OrderBy.DATE).setSearchQuery("").setLimit(20));
        options2.setValue(new ProductListOptions().setOrderBy(OrderBy.RATING).setSearchQuery("").setLimit(20));
        options3.setValue(new ProductListOptions().setOrderBy(OrderBy.BEST_SELL).setSearchQuery("").setLimit(20));
    }

    private void loadCats() {
        dataRepository.getCategories(new ResponseCallback<List<Category>>() {
            @Override
            public void onLoaded(List<Category> response) {
                updateMainCats(response);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void updateMainCats(List<Category> categories) {

        List<Category> mainCategories = new ArrayList<>();
        for (Category category : categories){
            if (category.getParent().equals(0))
                mainCategories.add(category);
        }

        mainCats.postValue(mainCategories);

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


    MutableLiveData<List<Category>> getMainCats() {
        return mainCats;
    }
}
