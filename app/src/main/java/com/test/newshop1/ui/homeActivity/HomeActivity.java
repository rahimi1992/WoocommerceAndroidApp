package com.test.newshop1.ui.homeActivity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.newshop1.R;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.ui.BaseActivity;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.utilities.InjectorUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
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
        container.addView(firstCardView);
        container.addView(secondCardView);

        RecyclerView firstRecyclerView = setupCardView(firstCardView);
        RecyclerView secondRecyclerView = setupCardView(secondCardView);

        HomeProductListAdapter firstAdapter = new HomeProductListAdapter();
        firstRecyclerView.setAdapter(firstAdapter);

        HomeProductListAdapter secondAdapter = new HomeProductListAdapter();
        secondRecyclerView.setAdapter(secondAdapter);


        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);

        viewModel.getNewProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Log.d(TAG, "onChanged: size: " + products.size());
                firstAdapter.submitList(products);
            }
        });

        viewModel.getOnSaleProducts().observe(this, secondAdapter::submitList);

    }

    private RecyclerView setupCardView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(createOnScrollListener(layoutManager, view));
        recyclerView.addItemDecoration(new CustomItemDecoration());
        recyclerView.scrollToPosition(-1);
        //(new LinearSnapHelper()).attachToRecyclerView(recyclerView);

        return recyclerView;
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


}
