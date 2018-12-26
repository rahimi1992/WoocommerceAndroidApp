package com.test.newshop1.ui.productListActivity;


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

import com.test.newshop1.R;
import com.test.newshop1.data.OrderBy;
import com.test.newshop1.ui.OnItemClickListener;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.checkoutActivity.CheckoutActivity;
import com.test.newshop1.ui.detailActivity.DetailActivity;
import com.test.newshop1.utilities.BadgeDrawable;
import com.test.newshop1.utilities.InjectorUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ProductListActivity extends AppCompatActivity implements OnItemClickListener {

    public static final String FILTER_ID = "filter-q";
    private static final String TAG = "ProductListActivity";

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


        setContentView( R.layout.activity_product_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new GridLayoutManager(this, 1);
        int parentId = getIntent().getIntExtra(PARENT_ID, -1);
        int filter = getIntent().getIntExtra(FILTER_ID, -1);

        pagingAdapter = new ProductListAdapter(ProductListAdapter.LINEAR_VIEW_TYPE);
        pagingAdapter.setOnItemClickListener(this);

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        viewModel = ViewModelProviders.of(this, factory).get(ProductListActivityViewModel.class);


        viewModel.setOrderBy(OrderBy.RATING);
        viewModel.setParentId(parentId);
        viewModel.getProducts().observe(this, pagingAdapter::submitList);




        //binding.containerRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerView recyclerView = findViewById(R.id.container_RV);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pagingAdapter);
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

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(
                searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        return super.onCreateOptionsMenu(menu);
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
                break;
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
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
