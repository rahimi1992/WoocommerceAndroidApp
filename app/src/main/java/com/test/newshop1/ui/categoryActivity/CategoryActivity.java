package com.test.newshop1.ui.categoryActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.google.android.material.tabs.TabLayout;
import com.test.newshop1.R;
import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.ui.BaseActivity;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.checkoutActivity.CheckoutActivity;
import com.test.newshop1.ui.productListActivity.ProductListActivity;
import com.test.newshop1.utilities.BadgeDrawable;
import com.test.newshop1.utilities.InjectorUtil;

import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class CategoryActivity extends BaseActivity implements OnCatItemClickListener {

    public static final String DEFAULT_SELECTED_CAT = "default-cat";
    private CategoryViewModel viewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CategoryFragmentPagerAdapter pagerAdapter;
    private LayerDrawable cartIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        tabLayout = findViewById(R.id.main_cat_tabs);
        viewPager = findViewById(R.id.container_VP);

        pagerAdapter = new CategoryFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setRotationY(180);

        viewModel = obtainViewModel(this);
        viewModel.loadCategories().observe(this, isLoaded -> {
            if (isLoaded != null && isLoaded) {
                setupTabLayout(getIntent().getIntExtra(DEFAULT_SELECTED_CAT, 0));
            }
        });


    }

    public static CategoryViewModel obtainViewModel(FragmentActivity activity) {

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(activity);

        return ViewModelProviders.of(activity, factory).get(CategoryViewModel.class);
    }

    private void setupTabLayout(int selected) {
        List<Category> mainCategories = viewModel.getCategories(0);
        for (Category category : mainCategories) {
            pagerAdapter.addPages(category.getId(), category.getName());
        }

        viewPager.setCurrentItem(selected, true);
        tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        cartIcon = (LayerDrawable) menu.findItem(R.id.cart_item).getIcon();
        viewModel.getCartItemCount().observe(this, this::setCount);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(
                searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_item:
                startCheckout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        supportInvalidateOptionsMenu();
        super.onResume();
    }

    public void setCount(Integer count) {

        BadgeDrawable badge;
        Drawable reuse = cartIcon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(this);
        }

        badge.setCount(count == null ? "0" : String.valueOf(count));
        cartIcon.mutate();
        cartIcon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

    private void startCheckout() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
    }


    private void showProductList(int id) {

        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra(ProductListActivity.PARENT_ID, id);
        startActivity(intent);

    }

    @Override
    public void onItemClicked(int id, int position) {
        showProductList(id);
    }
}
