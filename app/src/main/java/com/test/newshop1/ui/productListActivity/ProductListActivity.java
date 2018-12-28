package com.test.newshop1.ui.productListActivity;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.test.newshop1.R;
import com.test.newshop1.data.OrderBy;
import com.test.newshop1.data.database.category.Category;
import com.test.newshop1.ui.OnItemClickListener;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.categoryActivity.SubCatRecyclerViewAdapter;
import com.test.newshop1.ui.checkoutActivity.CheckoutActivity;
import com.test.newshop1.ui.detailActivity.DetailActivity;
import com.test.newshop1.utilities.BadgeDrawable;
import com.test.newshop1.utilities.InjectorUtil;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ProductListActivity extends AppCompatActivity implements OnItemClickListener {

    public static final String ORDER_BY = "order-by";
    private static final String TAG = "ProductListActivity";

    public static final String PARENT_ID = "parent-id";
    private ProductListActivityViewModel viewModel;
    private LayerDrawable cartIcon;
    private MenuItem toggleGridMenuItem;
    private ProductListAdapter pagingAdapter;
    private GridLayoutManager layoutManager;
    private SubCatRecyclerViewAdapter catAdapter;
    private int lastSelectedSubCatPos = -1;
    private int parentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);


        setContentView( R.layout.activity_product_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new GridLayoutManager(this, 1);
        parentId = getIntent().getIntExtra(PARENT_ID, -1);
        OrderBy orderBy = (OrderBy) getIntent().getSerializableExtra(ORDER_BY);
        if (orderBy == null){
            orderBy = OrderBy.DATE;
        }

        Log.d(TAG, "onCreate: orderby" + orderBy);

        pagingAdapter = new ProductListAdapter(ProductListAdapter.LINEAR_VIEW_TYPE);
        pagingAdapter.setOnItemClickListener(this);

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        viewModel = ViewModelProviders.of(this, factory).get(ProductListActivityViewModel.class);



        viewModel.setParentId(parentId);
        viewModel.getProducts().observe(this, products -> {
            pagingAdapter.submitList(products);
            Log.d(TAG, "onChanged: called");
        });

        viewModel.loadCategories().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoaded) {
                if (isLoaded) {
                    updateSubCats(parentId);
                }
            }
        });


        //binding.containerRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerView productRecyclerView = findViewById(R.id.container_RV);
        RecyclerView catRecyclerView = findViewById(R.id.sub_cat_container_RV);
        catRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        catAdapter = new SubCatRecyclerViewAdapter(this::showProductList);
        catAdapter.setViewType(SubCatRecyclerViewAdapter.SIMPLE_VIEW_TYPE);
        catRecyclerView.setAdapter(catAdapter);
        productRecyclerView.setLayoutManager(layoutManager);
        productRecyclerView.setAdapter(pagingAdapter);

        AppCompatSpinner spinner = findViewById(R.id.order_by_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.order_by_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Log.d(TAG, "onItemSelected: order by date called");
                        viewModel.setOrderBy(OrderBy.DATE);
                        break;
                    case 1:
                        viewModel.setOrderBy(OrderBy.RATING);
                        break;
                    case 2:
                        viewModel.setOrderBy(OrderBy.BEST_SELL);
                        break;
                    case 3:
                        viewModel.setOrderBy(OrderBy.PRICE_ASC);
                        break;
                    case 4:
                        viewModel.setOrderBy(OrderBy.PRICE_DESC);

                }
                pagingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] titles = getResources().getStringArray(R.array.home_page_card_titles);
        switch (orderBy){
            case DATE:
                getSupportActionBar().setTitle(titles[0]);
                spinner.setSelection(0);
                break;
            case RATING:
                getSupportActionBar().setTitle(titles[1]);
                spinner.setSelection(1);
                break;
            case BEST_SELL:
                getSupportActionBar().setTitle(titles[2]);
                spinner.setSelection(2);
                break;
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int parentId = intent.getIntExtra(PARENT_ID, -1);
        //updateSubCats(parentId);
        if (parentId != -1) {
            viewModel.setParentId(parentId);
        }


    }

    private void updateSubCats(int parent) {

        List<Category> subCategories = viewModel.getCategories(parent);
        catAdapter.submitList(subCategories);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(viewModel.getCatName(parent));
        }

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

    private void showProductList(int id, int position) {

        Intent intent = new Intent(this, ProductListActivity.class);
        catAdapter.selectItem(position);
        if (lastSelectedSubCatPos != -1){
            catAdapter.unSelectItem(lastSelectedSubCatPos);
        }
        if (lastSelectedSubCatPos != position){
            lastSelectedSubCatPos = position;
            intent.putExtra(ProductListActivity.PARENT_ID, id);
        } else {
            lastSelectedSubCatPos = -1;
            intent.putExtra(ProductListActivity.PARENT_ID, parentId);
        }

        startActivity(intent);

    }
}
