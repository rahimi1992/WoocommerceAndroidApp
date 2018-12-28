package com.test.newshop1.ui.homeActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.newshop1.R;
import com.test.newshop1.data.OrderBy;
import com.test.newshop1.ui.BaseActivity;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.categoryActivity.CategoryActivity;
import com.test.newshop1.ui.categoryActivity.SubCatRecyclerViewAdapter;
import com.test.newshop1.ui.detailActivity.DetailActivity;
import com.test.newshop1.ui.productListActivity.ProductListActivity;
import com.test.newshop1.ui.productListActivity.ProductListAdapter;
import com.test.newshop1.utilities.InjectorUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";


    private String bannerImages[] = {"https://femelo.com/wp-content/uploads/2018/09/banner_products.jpg",
            "https://femelo.com/wp-content/uploads/2018/06/Untitled.png"};

    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new BannerPagerAdapter(bannerImages));

        LinearLayout container = findViewById(R.id.container_ll);
        View firstCardView = getLayoutInflater().inflate(R.layout.parallax_bg_recyclerview, container, false);
        View secondCardView = getLayoutInflater().inflate(R.layout.parallax_bg_recyclerview, container, false);
        View thirdCardView = getLayoutInflater().inflate(R.layout.parallax_bg_recyclerview, container, false);
        container.addView(firstCardView);
        container.addView(secondCardView);
        container.addView(thirdCardView);

        RecyclerView catRecyclerView = findViewById(R.id.main_cat_container_RV);
        catRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        SubCatRecyclerViewAdapter catAdapter = new SubCatRecyclerViewAdapter(this::startCategoryActivity);
        catAdapter.setViewType(SubCatRecyclerViewAdapter.SIMPLE_VIEW_TYPE);
        catRecyclerView.setAdapter(catAdapter);


        HomeProductListAdapter firstAdapter = new HomeProductListAdapter();
        firstRecyclerView.setAdapter(firstAdapter);
        firstAdapter.setOnItemClickListener(this::onItemClicked);

        HomeProductListAdapter secondAdapter = new HomeProductListAdapter();
        secondRecyclerView.setAdapter(secondAdapter);
        secondAdapter.setOnItemClickListener(this::onItemClicked);

        HomeProductListAdapter thirdAdapter = new HomeProductListAdapter();
        thirdRecyclerView.setAdapter(thirdAdapter);
        thirdAdapter.setOnItemClickListener(this::onItemClicked);

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);

        viewModel.getMainCats().observe(this, catAdapter::submitList);

        viewModel.getNewProducts().observe(this, firstAdapter::submitList);

        viewModel.getOnSaleProducts().observe(this, secondAdapter::submitList);

        viewModel.getFeaturedProducts().observe(this, thirdAdapter::submitList);

    }

    private RecyclerView setupCardView(View view, int position) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        ((TextView) view.findViewById(R.id.title)).setText(getResources().getStringArray(R.array.home_page_card_titles)[position]);
        ((TextView) view.findViewById(R.id.second_title)).setText(getResources().getStringArray(R.array.home_page_card_titles)[position]);
        view.findViewById(R.id.show_more).setOnClickListener(v -> showMore(position));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(createOnScrollListener(layoutManager, view));
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(0,0);
        marginLayoutParams.setMargins(4, 40, 160, 5);
        recyclerView.addItemDecoration(new CustomItemDecoration(marginLayoutParams));
        recyclerView.scrollToPosition(-1);
        //(new LinearSnapHelper()).attachToRecyclerView(recyclerView);

        return recyclerView;
    }

    private void showMore(int position) {
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra(ProductListActivity.FILTER_ID, position);
        startActivity(intent);

    }

    @NonNull
    private RecyclerView.OnScrollListener createOnScrollListener(LinearLayoutManager layoutManager, View container) {

        ImageView imageView = container.findViewById(R.id.bg_parallax_bg_iv);
        View cover = container.findViewById(R.id.bg_parallax_alpha_view);
        View title = container.findViewById(R.id.title);

        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findFirstVisibleItemPosition()==0) {
                    // Get view of the first item
                    View firstVisibleItem = layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition());
                    float density = getResources().getDisplayMetrics().density;
                    int width = getResources().getDisplayMetrics().widthPixels;
                    float distanceFromRight = firstVisibleItem.getLeft(); // distance from the left
                    //Log.d(TAG, "onScrolled: width: "+ width + "distance from right: " + distanceFromRight);
                    float translateX = (int) distanceFromRight *  0.12f; // move x distance
                    imageView.setTranslationX(translateX);
                    title.setTranslationX(translateX);

                    float itemSize = firstVisibleItem.getWidth(); // view size
                    float alpha = (Math.abs(distanceFromRight) / width); // view transparency
                    //Log.d(TAG, "onScrolled: alpha: " + alpha);
                    //Set alpha to image to bring 'fade out' and 'fade in' effect
                    imageView.setAlpha(1 - alpha);
                    title.setAlpha(1-alpha);
                    //Set alpha to color view to bring 'darker' and 'clearer' effect
                    cover.setAlpha(alpha);

                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(
                searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);

        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        return true;
    }

    @Override
    protected void onResume() {

        supportInvalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemClicked(int productId) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.PRODUCT_ID, productId);
        startActivity(intent);
    }

    private void startCategoryActivity(int id, int position) {

        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CategoryActivity.DEFAULT_SELECTED_CAT, position);
        startActivity(intent);

    }


}
