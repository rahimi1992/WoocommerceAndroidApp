package com.test.newshop1.ui.categoryActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.test.newshop1.R;
import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.ui.BaseActivity;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.productListActivity.ProductListActivity;
import com.test.newshop1.utilities.InjectorUtil;

import java.util.List;

public class CategoryActivity extends BaseActivity {

    private static final String TAG = "CategoryActivity";
    private static int LAST_PARENT_ID = -1;
    private static String LAST_PARENT_NAME;
    private static boolean active = false;
    private CategoryViewModel mViewModel;
    private CategoryRecyclerViewAdapter categoryAdapter;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setTitle("");

        RecyclerView recyclerView = findViewById(R.id.container_RV);

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(CategoryViewModel.class);
        mViewModel.loadCategories().observe(this, isLoaded -> {
            if (isLoaded != null && isLoaded){
                showSubCategories(0);
            }
        });

        categoryAdapter = new CategoryRecyclerViewAdapter();
        categoryAdapter.setOnItemClickListener(category -> {
            LAST_PARENT_ID = category.getParent();
            LAST_PARENT_NAME = category.getName();
            showSubCategories(category.getId());
        });
        layoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryAdapter);
    }


    private void showSubCategories(int id) {

        List<Category> subCategories = mViewModel.getCategories(id);

        if (id == 0){
            for (Category category : subCategories) {
                category.setSubCatTitles(mViewModel.getSubCatTitles(category.getId()));
            }
            layoutManager.setSpanCount(2);
        } else {
            layoutManager.setSpanCount(1);
        }

        if (subCategories.size() < 1) {
            updateLastParent();
            showProductList(id);
        } else {
            updateTitle();
            categoryAdapter.loadNewData(subCategories, true);
        }
    }

    private void updateTitle() {
        getSupportActionBar().setTitle(LAST_PARENT_NAME);
    }

    private void showProductList(int id) {

        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra(ProductListActivity.PARENT_ID, id);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Log.d(TAG, "onBackPressed: " + LAST_PARENT_ID);
        if (LAST_PARENT_ID == -1 || drawer.isDrawerOpen(GravityCompat.START))
            super.onBackPressed();
        else {

            showSubCategories(LAST_PARENT_ID);
            updateLastParent();
        }
    }

    private void updateLastParent() {
        Category parentCategory = mViewModel.getParent(LAST_PARENT_ID);
        if (parentCategory != null) {
            LAST_PARENT_ID = parentCategory.getParent();
            LAST_PARENT_NAME = parentCategory.getName();
        } else {
            LAST_PARENT_ID = -1;
            LAST_PARENT_NAME = "";
        }
        updateTitle();
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    public static boolean isActive(){return active;}

}
