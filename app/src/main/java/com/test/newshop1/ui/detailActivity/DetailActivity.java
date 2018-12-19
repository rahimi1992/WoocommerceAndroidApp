package com.test.newshop1.ui.detailActivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.test.newshop1.R;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.ui.OnItemClickListener;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.checkoutActivity.CheckoutActivity;
import com.test.newshop1.utilities.BadgeDrawable;
import com.test.newshop1.utilities.InjectorUtil;
import com.test.newshop1.utilities.PersianTextUtil;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity implements ProductImageSliderAdapter.OnCallback, OnItemClickListener {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String PRODUCT_ID = "product-id";


    private DetailActivityViewModel viewModel;

    private ViewPager slider;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private PageIndicatorView pageIndicatorView;
    private List<Integer> colors = new ArrayList<>();
    private CustomCardView customCardView;
    private Button expandBtn;
    private LayerDrawable cartIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.anim_toolbar);

        setSupportActionBar(toolbar);

        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);


        collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.transparentDark));
        collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.transparentDark));

        appBarLayout = findViewById(R.id.appbar);

        slider = findViewById(R.id.slider_view_pager);
        pageIndicatorView = findViewById(R.id.slider_view_pager_indicator);


        customCardView = findViewById(R.id.detail_card);
        expandBtn = findViewById(R.id.expand_btn);
        expandBtn.setText("ادامه...");
        expandBtn.setOnClickListener(v -> customCardView.toggle(expandBtn));

        NormalProductListAdapter adapter = new NormalProductListAdapter();
        adapter.setListener(this);

        int productId = getIntent().getIntExtra(PRODUCT_ID, 0);
        viewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel.class);
        viewModel.getProduct(productId).observe(this, this::updateUI);
        viewModel.getRelatedProducts().observe(this, adapter::setProducts);

        RecyclerView relatedRV = findViewById(R.id.related_RV);

        relatedRV.setAdapter(adapter);
        relatedRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        findViewById(R.id.add_to_cart_btn).setOnClickListener(v -> viewModel.addToCart());
    }

    private void setObservers(){

    }

    private void updateUI(Product product) {
        if (product != null) {
            colors.clear();
            for (int i = 0; i < product.getImages().size(); i++) {
                colors.add(getResources().getColor(R.color.colorAccent));
            }
            ProductImageSliderAdapter sliderAdapter = new ProductImageSliderAdapter(product.getImages(), this);
            slider.setAdapter(sliderAdapter);
            collapsingToolbar.setTitle(product.getName());

            TextView pRegularPrice = findViewById(R.id.regular_price);
            int price = product.getPrice().equals("") ? 0 : Integer.valueOf(product.getPrice());
            int regularPrice = product.getRegularPrice().equals("") ? 0 : Integer.valueOf(product.getRegularPrice());
            pRegularPrice.setText(getResources().getString(R.string.currency,
                    PersianTextUtil.toPer(product.getRegularPrice())));
            pRegularPrice.setPaintFlags(pRegularPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (price >= regularPrice) {
                pRegularPrice.setVisibility(View.GONE);
            }
            TextView pPrice = findViewById(R.id.product_price);
            pPrice.setText(getResources().getString(R.string.currency,
                    PersianTextUtil.toPer(product.getPrice())));
            WebView pDescription = findViewById(R.id.product_description);
            String rawText = product.getDescription();
            pDescription.loadDataWithBaseURL(null, "<html dir=\"rtl\" style=\"text-align:justify; width:100%;\" lang=\"\"><body>" + rawText + "</body></html>", "text/html", "utf-8", null);

        }

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
    public void callbackColor(int color, int position) {
        //Log.d(TAG, "callbackColor: color: " + color);
        //colors.set(position, color);
        //int page = slider.getCurrentItem();
        //collapsingToolbar.setContentScrimColor(colors.get(page));
        //pageIndicatorView.setSelectedColor(colors.get(page));
        //invalidateOptionsMenu();
    }

    @Override
    public void startPostponedTransition() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: called");
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        cartIcon = (LayerDrawable) menu.findItem(R.id.cart_item).getIcon();

        viewModel.getCartItemCount().observe(this, this::setCount);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_item:
                startCheckout();
        }
        return true;
    }

    private void startCheckout() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(int productId) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.PRODUCT_ID, productId);
        startActivity(intent);
    }
}
