package com.test.newshop1.ui.categoryActivity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.test.newshop1.data.ResponseCallback;
import com.test.newshop1.data.DataRepository;
import com.test.newshop1.data.database.category.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel implements ResponseCallback<List<Category>> {
    private DataRepository dataRepository;
    private MutableLiveData<Boolean> isCategoriesLoaded;
    private List<Category> allCategories;

    public CategoryViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        isCategoriesLoaded = new MutableLiveData<>();
    }

    MutableLiveData<Boolean> loadCategories(){
        dataRepository.getCategories(this);
        return isCategoriesLoaded;
    }

    @Override
    public void onLoaded(List<Category> categories) {
        this.allCategories = categories;
        this.isCategoriesLoaded.postValue(true);

    }

    @Override
    public void onDataNotAvailable() {

    }

    List<Category> getCategories(int parent){
        List<Category> categories = new ArrayList<>();
        for (Category category : allCategories){
            if (category.getParent().equals(parent))
                categories.add(category);
        }
        return categories;
    }

    Category getParent(int lastParent) {
        if (lastParent == 0)
            return null;
        else {
            for (Category category : allCategories){
                if (category.getId().equals(lastParent))
                    return category;
            }
        }
        return null;

    }

    List<String> getSubCatTitles(int parent) {
        List<String> subCatTitles = new ArrayList<>();
        for (Category category : allCategories){
            if (category.getParent().equals(parent))
                subCatTitles.add(category.getName());
        }
        return subCatTitles;
    }
}
