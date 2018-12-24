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

import com.test.newshop1.R;
import com.test.newshop1.ui.BaseActivity;
import com.test.newshop1.ui.ViewModelFactory;
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



        RecyclerView recyclerView = findViewById(R.id.container_RV1);
        HomeProductListAdapter adapter = new HomeProductListAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //(new LinearSnapHelper()).attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new CustomItemDecoration());
        recyclerView.scrollToPosition(-1);
        ImageView imageView = findViewById(R.id.bg_parallax_bg_iv1);
        View cover = findViewById(R.id.bg_parallax_alpha_view1);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findFirstVisibleItemPosition()==0) {
                    // Get view of the first item
                    View firstVisibleItem = layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition());
                    float distanceFromLeft = firstVisibleItem.getLeft(); // distance from the left
                    float translateX = (int) distanceFromLeft * 0.18f; // move x distance
                    imageView.setTranslationX(translateX);


                        float itemSize = firstVisibleItem.getWidth(); // view size
                        float alpha = (Math.abs(distanceFromLeft) / itemSize * 0.20f); // view transparency

                        //Set alpha to image to bring 'fade out' and 'fade in' effect
                        imageView.setAlpha(1 - alpha);
                        //Set alpha to color view to bring 'darker' and 'clearer' effect
                        cover.setAlpha(alpha);

                }
            }
        });


        RecyclerView recyclerView2 = findViewById(R.id.container_RV2);
        HomeProductListAdapter adapter2 = new HomeProductListAdapter();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(adapter2);
        //(new LinearSnapHelper()).attachToRecyclerView(recyclerView);
        recyclerView2.addItemDecoration(new CustomItemDecoration());
        recyclerView2.scrollToPosition(-1);
        ImageView imageView2 = findViewById(R.id.bg_parallax_bg_iv2);
        View cover2 = findViewById(R.id.bg_parallax_alpha_view2);
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager2.findFirstVisibleItemPosition()==0) {
                    // Get view of the first item
                    View firstVisibleItem = layoutManager2.findViewByPosition(layoutManager2.findFirstVisibleItemPosition());
                    float distanceFromLeft = firstVisibleItem.getLeft(); // distance from the left
                    float translateX = (int) distanceFromLeft * 0.18f; // move x distance
                    imageView2.setTranslationX(translateX);


                    float itemSize = firstVisibleItem.getWidth(); // view size
                    float alpha = (Math.abs(distanceFromLeft) / itemSize * 0.20f); // view transparency

                    //Set alpha to image to bring 'fade out' and 'fade in' effect
                    imageView2.setAlpha(1 - alpha);
                    //Set alpha to color view to bring 'darker' and 'clearer' effect
                    cover2.setAlpha(alpha);

                }
            }
        });

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        viewModel.getProducts1().observe(this, adapter::submitList);
        viewModel.setParent1(132);
        viewModel.getProducts2().observe(this, adapter2::submitList);
        viewModel.setParent2(120);




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
