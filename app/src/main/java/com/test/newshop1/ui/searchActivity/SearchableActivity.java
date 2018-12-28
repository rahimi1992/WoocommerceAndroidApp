package com.test.newshop1.ui.searchActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.test.newshop1.R;
import com.test.newshop1.data.OrderBy;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.detailActivity.DetailActivity;
import com.test.newshop1.ui.productListActivity.ProductListAdapter;
import com.test.newshop1.utilities.InjectorUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchableActivity extends AppCompatActivity{
    private static final String TAG = "SearchableActivity";

    String query;

    private SearchViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);}

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel.class);

        ProductListAdapter adapter = new ProductListAdapter(ProductListAdapter.LINEAR_VIEW_TYPE);
        adapter.setOnItemClickListener(this::onItemClicked);
        RecyclerView recyclerView = findViewById(R.id.container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        viewModel.getProducts().observe(this, adapter::submitList);
        handleIntent(getIntent());

        AppCompatSpinner spinner = findViewById(R.id.order_by_spinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.order_by_items, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
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
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            doMySearch();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expand_search, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(
                searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setIconified(false);
        searchView.setQuery(query,false);
        searchView.clearFocus();

        return true;
    }


    public void onItemClicked(int productId) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.PRODUCT_ID, productId);
        startActivity(intent);
    }

    private void doMySearch() {
        viewModel.setSearchQuery(query);
    }

}
