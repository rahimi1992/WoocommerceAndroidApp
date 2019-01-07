package com.test.newshop1.ui.checkoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.test.newshop1.R;
import com.test.newshop1.data.database.shoppingcart.CartItem;
import com.test.newshop1.ui.detailActivity.DetailActivity;
import com.test.newshop1.utilities.PersianTextUtil;
import com.test.newshop1.utilities.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class CartFragment extends Fragment {

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
        if (isCartEmpty) {
            Snackbar.make(Objects.requireNonNull(getView()), R.string.empty_cart_text, Snackbar.LENGTH_SHORT).show();
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

    private void showCartDetail() {
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
                Button deleteButton = viewItem.findViewById(R.id.delete_btn);
                deleteButton.setOnClickListener(this::deleteItem);
                deleteButton.setId(i);
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
                String itemPriceText = getString(R.string.currency, PersianTextUtil.toPer(Integer.valueOf(item.getTotal()) / item.getQuantity()));
                priceTV.setText(itemPriceText);
                CircleImageView image = viewItem.findViewById(R.id.thumbnail);
                image.setOnClickListener(this::goToProductDetail);
                image.setId(item.getProductId());
                Picasso.get().load(item.getImageSrc())
                        .error(R.drawable.place_holder)
                        .placeholder(R.drawable.place_holder)
                        .into(image);
                linearLayout.addView(viewItem);
                price += Integer.valueOf(item.getTotal());
            }
            viewItem = inflater.inflate(R.layout.cart_summary, linearLayout, false);
            TextView totalPriceTV = viewItem.findViewById(R.id.s_total_price);
            totalPriceTV.setText(getString(R.string.currency, PersianTextUtil.toPer(price)));
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

    private void deleteItem(View view) {
        mViewModel.deleteItem(view.getId());
        SnackbarUtils.showSnackbar(getView(), getString(R.string.removed_text), R.string.undo_delete, v -> mViewModel.undoRemove());
    }


    private void increaseQuantity(View view) {
        mViewModel.addItem(view.getId());
    }

    private void decreaseQuantity(View view) {
        mViewModel.decreaseItem(view.getId());
    }

}
