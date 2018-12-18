package com.test.newshop1.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.newshop1.utilities.ImageUtil;
import com.test.newshop1.utilities.PersianTextUtil;

public class BindingAdapters {
    @BindingAdapter({"imageUrl", "error", "thumb"})
    public static void loadImage(ImageView view, String url, Drawable error, boolean isThumb) {
        if (isThumb)
            url = ImageUtil.getThumb(url, ImageUtil.MEDIUM_SIZE);

        Picasso.get().load(url).error(error).into(view);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("showIfNull")
    public static void showIfNull(View view, Object object) {
        view.setVisibility(object == null ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("hideIfNull")
    public static void hideIfNull(View view, Object object) {
        view.setVisibility(object == null ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter("visibleGoneLoading")
    public static void showHideLoading(View view, boolean show) {
        if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("invisibleIfFalse")
    public static void invisibleIfFalse(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("setElevation")
    public static void setElevation(CardView view, boolean isSelected) {
        view.setCardElevation(isSelected ? 10 : 30);
    }

    @BindingAdapter({"price", "regularPrice"})
    public static void setBadgeText(TextView view, String price, String regularPrice) {

        Double priceD = price.equals("")?0:Double.valueOf(price);
        Double regularPriceD = regularPrice.equals("")?0:Double.valueOf(regularPrice);
        String text = PersianTextUtil.toPer( Math.round(100.0 - priceD / regularPriceD * 100.0)) + "%";
        view.setText(text);
    }

}
