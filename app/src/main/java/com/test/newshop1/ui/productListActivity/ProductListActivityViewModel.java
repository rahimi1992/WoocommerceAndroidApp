package com.test.newshop1.ui.productListActivity;


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


public class ProductListActivityViewModel extends ViewModel implements ResponseCallback<List<Category>> {

    private MutableLiveData<ProductListOptions> options = new MutableLiveData<>();

    private DataRepository mRepository;

    private MutableLiveData<Boolean> isCategoriesLoaded;
    private List<Category> allCategories;


    private LiveData<PagedList<Product>> mProducts = Transformations.switchMap(options, (opt) -> mRepository.getProducts(opt));
    public ProductListActivityViewModel(DataRepository repository) {

        mRepository = repository;
        options.setValue(new ProductListOptions());
        isCategoriesLoaded = new MutableLiveData<>();

    }

    MutableLiveData<Boolean> loadCategories(){
        mRepository.getCategories(this);
        return isCategoriesLoaded;
    }

    List<Category> getCategories(int parent){
        List<Category> categories = new ArrayList<>();
        for (Category category : allCategories){
            if (category.getParent().equals(parent))
                categories.add(category);
        }
        return categories;
    }

    String getCatName(int id){
        for (Category category : allCategories){
            if (category.getId().equals(id))
                return category.getName();
        }
        return " ";
    }

    @Override
    public void onLoaded(List<Category> categories) {
        this.allCategories = categories;
        this.isCategoriesLoaded.postValue(true);

    }

    @Override
    public void onDataNotAvailable() {

    }

    LiveData<PagedList<Product>> getProducts(){
        return mProducts;
    }


    void setParentId(int parentId) {
        if (options.getValue() != null) {
            if (parentId != -1) {
                options.setValue(options.getValue().setParentId(parentId));
            } else {
                options.setValue(options.getValue().setSearchQuery(""));
            }
        }
    }


    void setOrderBy(OrderBy orderBy){
        if (options.getValue() != null)
            options.setValue(options.getValue().setOrderBy(orderBy));

    }

    LiveData<Integer> getCartItemCount() {
        return mRepository.getCartItemCount();
    }

}
