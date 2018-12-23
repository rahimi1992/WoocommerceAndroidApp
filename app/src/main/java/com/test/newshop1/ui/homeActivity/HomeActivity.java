package com.test.newshop1.ui.homeActivity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.test.newshop1.R;
import com.test.newshop1.ui.BaseActivity;

import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends BaseActivity {


    private String bannerImages[] = {"https://femelo.com/wp-content/uploads/2018/09/banner_products.jpg",
            "https://femelo.com/wp-content/uploads/2018/06/Untitled.png"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new BannerPagerAdapter(bannerImages));
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
