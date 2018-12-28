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
    private MutableLiveData<List<Category>> mainCats = new MutableLiveData<>();

    private MutableLiveData<ProductListOptions> options1 = new MutableLiveData<>();
    private MutableLiveData<ProductListOptions> options2 = new MutableLiveData<>();
    private MutableLiveData<ProductListOptions> options3 = new MutableLiveData<>();

    private LiveData<PagedList<Product>> mProducts1 = Transformations.switchMap(options1, (opt) -> dataRepository.getProducts(opt));
    private LiveData<PagedList<Product>> mProducts2 = Transformations.switchMap(options2, (opt) -> dataRepository.getProducts(opt));
    private LiveData<PagedList<Product>> mProducts3 = Transformations.switchMap(options3, (opt) -> dataRepository.getProducts(opt));

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


    LiveData<PagedList<Product>> getNewProducts() {
        return mProducts1;
    }

    LiveData<PagedList<Product>> getBestSellProducts() {
        return mProducts2;
    }

    LiveData<PagedList<Product>> getPopProducts() {
        return mProducts3;
    }


    MutableLiveData<List<Category>> getMainCats() {
        return mainCats;
    }
}
