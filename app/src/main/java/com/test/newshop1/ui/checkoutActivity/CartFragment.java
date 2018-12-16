package com.test.newshop1.ui.checkoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.newshop1.R;
import com.test.newshop1.data.database.shoppingcart.CartItem;
import com.test.newshop1.ui.detailActivity.DetailActivity;
import com.test.newshop1.utilities.PersianTextUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartFragment extends Fragment {
    private static final String TAG = CartFragment.class.getSimpleName();

    private CheckoutViewModel mViewModel;
    private List<CartItem> cartItems;
    private LinearLayout linearLayout;
    private Boolean isCartEmpty = true;
    private TextView emptyCartText;


    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.checkout_cart_frag, container, false);
        linearLayout = root.findViewById(R.id.items_container);
        root.findViewById(R.id.next_btn).setOnClickListener(view -> nextStep());
        emptyCartText = root.findViewById(R.id.empty_cart_tv);
        return root;
    }

    private void nextStep() {
        if (isCartEmpty){
            Snackbar.make(getView(), R.string.empty_cart_text, Snackbar.LENGTH_SHORT).show();
        } else {
            mViewModel.setCurrentStep(CheckoutStep.ADDRESS);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cartItems = new ArrayList<>();
        mViewModel = CheckoutActivity.obtainViewModel(getActivity());
        mViewModel.getCartItemsLD().observe(this, items -> {
            cartItems = items;
            showCartDetail();
        });

    }

    void showCartDetail() {
        Log.d(TAG, "showCartDetail: starts");
        LayoutInflater inflater = getLayoutInflater();
        int price = 0;
        linearLayout.removeAllViews();
        if (cartItems != null && cartItems.size() != 0) {

            View viewItem;

            for (int i = 0; i < cartItems.size(); i++) {
                CartItem item = cartItems.get(i);
                int itemId = item.getId();
                viewItem = inflater.inflate(R.layout.cart_item, linearLayout, false);
                TextView title = viewItem.findViewById(R.id.item_title);
                //title.setTypeface(font_yekan);
//                if (item.hasMetaData()) {
//                    TextView metaData = viewItem.findViewById(R.id.meta_data_TV);
//                    String metaText = item.getMetaData().get(0).getKey() + ": " + item.getMetaData().get(0).getValue();
//                    metaData.setText(metaText);
//                }
                Button deleteButton = viewItem.findViewById(R.id.delete_btn);
                deleteButton.setOnClickListener(this::deleteItem);
                deleteButton.setId(itemId);
                Button minusButton = viewItem.findViewById(R.id.minus_btn);
                minusButton.setOnClickListener(this::decreaseQuantity);
                minusButton.setId(itemId);
                Button plusButton = viewItem.findViewById(R.id.plus_btn);
                plusButton.setOnClickListener(this::increaseQuantity);
                plusButton.setId(itemId);
                title.setText(item.getName());
                TextView qtyTV = viewItem.findViewById(R.id.qty);
                qtyTV.setText(PersianTextUtil.toPer(item.getQuantity()));
                TextView priceTV = viewItem.findViewById(R.id.item_price);
                String itemPriceText = PersianTextUtil.toPer(item.getQuantity()) + " x " + PersianTextUtil.toPer(item.getTotal());
                priceTV.setText(itemPriceText);
                //priceTV.setTypeface(font_yekan);
                CircleImageView image = viewItem.findViewById(R.id.thumbnail);
                image.setOnClickListener(this::goToProductDetail);
                image.setId(item.getProductId());
                Picasso.get().load(item.getImageSrc())
                        .error(R.drawable.place_holder)
                        .placeholder(R.drawable.place_holder)
                        .into(image);
//                if (i == 0) {
//                    viewItem.findViewById(R.id.cart_header).setVisibility(View.VISIBLE);
//                }
                linearLayout.addView(viewItem);
                price += Integer.valueOf(item.getTotal()) * item.getQuantity();
            }
            viewItem = inflater.inflate(R.layout.cart_summary, linearLayout, false);
            TextView totalPriceTV = viewItem.findViewById(R.id.s_total_price);
            totalPriceTV.setText(PersianTextUtil.toPer(price));
            linearLayout.addView(viewItem);
            emptyCartText.setVisibility(View.GONE);
            isCartEmpty = false;
        } else {
            emptyCartText.setVisibility(View.VISIBLE);
            isCartEmpty = true;
        }

    }

    private void goToProductDetail(View v) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.PRODUCT_ID, v.getId());
        startActivity(intent);
    }

    public void deleteItem(View view) {
        mViewModel.deleteItem(view.getId());
    }


    public void increaseQuantity(View view) {
        mViewModel.addItem(view.getId());
    }

    public void decreaseQuantity(View view) {
        mViewModel.decreaseItem(view.getId());
    }

}
