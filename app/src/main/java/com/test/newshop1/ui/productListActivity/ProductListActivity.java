package com.test.newshop1.ui.productListActivity;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.test.newshop1.R;
import com.test.newshop1.databinding.ActivityProductListBinding;
import com.test.newshop1.ui.OnItemClickListener;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.checkoutActivity.CheckoutActivity;
import com.test.newshop1.ui.detailActivity.DetailActivity;
import com.test.newshop1.utilities.BadgeDrawable;
import com.test.newshop1.utilities.InjectorUtil;



public class ProductListActivity extends AppCompatActivity implements OnItemClickListener {

    public static final String PARENT_ID = "parent-id";
    private ProductListActivityViewModel viewModel;
    private LayerDrawable cartIcon;
    private MenuItem toggleGridMenuItem;
    private ProductListAdapter pagingAdapter;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);


        ActivityProductListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list);
        binding.setIsLoading(false);

        layoutManager = new GridLayoutManager(this, 1);
        int parentId = getIntent().getIntExtra(PARENT_ID, 0);
        pagingAdapter = new ProductListAdapter(ProductListAdapter.LINEAR_VIEW_TYPE);
        pagingAdapter.setOnItemClickListener(this);


        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        viewModel = ViewModelProviders.of(this, factory).get(ProductListActivityViewModel.class);

        viewModel.setParentId(parentId);
        viewModel.getProducts().observe(this, pagingAdapter::submitList);

        //binding.containerRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.containerRV.setLayoutManager(layoutManager);
        binding.containerRV.setAdapter(pagingAdapter);
    }

    @Override
    public void onItemClicked(int productId) {
        Intent intent = new Intent(ProductListActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.PRODUCT_ID, productId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        cartIcon = (LayerDrawable) menu.findItem(R.id.cart_item).getIcon();
        toggleGridMenuItem = menu.findItem(R.id.toggle_grid);

        viewModel.getCartItemCount().observe(this, this::setCount);
        return super.onCreateOptionsMenu(menu);
    }

    public void setCount(Integer count) {

        BadgeDrawable badge;
        Drawable reuse = cartIcon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(this);
        }

        badge.setCount(count ==null?"0": String.valueOf(count));
        cartIcon.mutate();
        cartIcon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart_item:
                startCheckout();
                break;
            case R.id.toggle_grid:
                toggleGrid();
        }
        return true;
    }

    private void toggleGrid() {
        switch (pagingAdapter.getItemViewType(0)) {
            case ProductListAdapter.LINEAR_VIEW_TYPE:
                pagingAdapter.setViewType(ProductListAdapter.GRID_VIEW_TYPE);
                layoutManager.setSpanCount(2);
                toggleGridMenuItem.setIcon(R.drawable.ic_list_view_large);
                break;
            case ProductListAdapter.GRID_VIEW_TYPE:
            pagingAdapter.setViewType(ProductListAdapter.GRID_VIEW_TYPE_LARGE);
            layoutManager.setSpanCount(1);
            toggleGridMenuItem.setIcon(R.drawable.ic_list_view);
            break;
            case ProductListAdapter.GRID_VIEW_TYPE_LARGE:
                pagingAdapter.setViewType(ProductListAdapter.LINEAR_VIEW_TYPE);
                layoutManager.setSpanCount(1);
                toggleGridMenuItem.setIcon(R.drawable.ic_grid_view);
                break;
        }

    }

    private void startCheckout() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
    }
}
