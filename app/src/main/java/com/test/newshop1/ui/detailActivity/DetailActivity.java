package com.test.newshop1.ui.detailActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.test.newshop1.R;
import com.test.newshop1.data.database.product.Product;
import com.test.newshop1.ui.OnItemClickListener;
import com.test.newshop1.ui.SnackbarMessageId;
import com.test.newshop1.ui.ViewModelFactory;
import com.test.newshop1.ui.checkoutActivity.CheckoutActivity;
import com.test.newshop1.utilities.BadgeDrawable;
import com.test.newshop1.utilities.InjectorUtil;
import com.test.newshop1.utilities.PersianTextUtil;
import com.test.newshop1.utilities.SnackbarUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


public class DetailActivity extends AppCompatActivity implements ProductImageSliderAdapter.OnCallback, OnItemClickListener {

    public static final String PRODUCT_ID = "product-id";


    private DetailActivityViewModel viewModel;

    private ViewPager slider;
    private CollapsingToolbarLayout collapsingToolbar;
    private CustomCardView customCardView;
    private Button expandBtn;
    private LayerDrawable cartIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Toolbar toolbar = findViewById(R.id.anim_toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ViewModelFactory factory = InjectorUtil.provideViewModelFactory(this);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);


        collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.transparentDark));
        collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.transparentDark));

        AppBarLayout appBarLayout1 = findViewById(R.id.appbar);
        appBarLayout1.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, verticalOffset) -> {
            if (toolbar.getNavigationIcon() != null && cartIcon != null)
                if ((collapsingToolbar.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(collapsingToolbar))) {
                    toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                    cartIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                } else {
                    toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                    cartIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
        });

        slider = findViewById(R.id.slider_view_pager);
        //pageIndicatorView = findViewById(R.id.slider_view_pager_indicator);


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
        viewModel.getSnackbarMessageId().observe(this, (SnackbarMessageId.SnackbarObserver)
                resourceId -> SnackbarUtils.showSnackbar(getWindow().getDecorView().getRootView(), getString(resourceId),
                        "مشاهده سبد خرید", v -> startCheckout()));

        RecyclerView relatedRV = findViewById(R.id.related_RV);

        relatedRV.setAdapter(adapter);
        relatedRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        findViewById(R.id.add_to_cart_btn).setOnClickListener(v -> viewModel.addToCart());
    }


    private void updateUI(Product product) {
        if (product != null) {

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
    }

    @Override
    public void startPostponedTransition() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                break;
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (upIntent != null) {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                        TaskStackBuilder.create(this)
                                .addNextIntentWithParentStack(upIntent)
                                .startActivities();
                    } else {
                        NavUtils.navigateUpTo(this, upIntent);
                    }
                }
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
