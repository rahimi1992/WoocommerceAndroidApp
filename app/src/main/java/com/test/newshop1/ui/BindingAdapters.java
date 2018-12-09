package com.test.newshop1.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.test.newshop1.utilities.ImageUtil;

public class BindingAdapters {
    @BindingAdapter({"imageUrl", "error", "thumb"})
    public static void loadImage(ImageView view, String url, Drawable error, boolean isThumb) {
        if (isThumb)
            url = ImageUtil.getThumb(url, ImageUtil.SMALL_SIZE);

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
}
