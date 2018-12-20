package com.test.newshop1.ui.homeActivity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.test.newshop1.R;
import com.test.newshop1.ui.BaseActivity;

import androidx.appcompat.widget.SearchView;

public class HomeActivity extends BaseActivity {

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(
                searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
        searchView.clearFocus();

        return true;
    }

    @Override
    protected void onResume() {
        if (searchView != null) {
            searchView.clearFocus();
        }

        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            //onSearchRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
