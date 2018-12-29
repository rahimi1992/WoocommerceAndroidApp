package com.test.newshop1.ui;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.newshop1.utilities.ImageUtil;
import com.test.newshop1.utilities.PersianTextUtil;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter({"imageUrl", "error", "thumb"})
    public static void loadImage(ImageView view, String url, Drawable error, boolean isThumb) {
        if (url != null) {
            if (isThumb)
                url = ImageUtil.getThumb(url, ImageUtil.MEDIUM_SIZE);
            Picasso.get().load(url).error(error).into(view);
        }
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

        if (price != null && regularPrice != null) {
            Double priceD = price.equals("") ? 0 : Double.valueOf(price);
            Double regularPriceD = regularPrice.equals("") ? 0 : Double.valueOf(regularPrice);
            String text = PersianTextUtil.toPer(Math.round(100.0 - priceD / regularPriceD * 100.0)) + "%";
            if (regularPriceD < priceD) {
                text = "";
            }
            view.setText(text);
        }
    }

    @BindingAdapter({"subTitles"})
    public static void setSubTitles(LinearLayout layout, List<String> titles){
        layout.removeAllViews();
        if (titles == null || titles.isEmpty()){
            return;
        }
        for (String title : titles) {
            TextView textView = new TextView(layout.getContext().getApplicationContext());
            textView.setText(title);
            layout.addView(textView);
        }

    }

    @BindingAdapter({"firstItem"})
    public static void setStartMargin(CardView view, boolean isFirst){
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lp.setMarginStart(isFirst? 100 : 5);
    }

    @BindingAdapter({"setRating"})
    public static void setRating(RatingBar view, String rating){
        if (rating != null && !rating.isEmpty()){
            view.setRating(Float.valueOf(rating));
        }
    }

    @BindingAdapter({"htmlContent"})
    public static void htmlContent(WebView view, String content){

        if (content != null && !content.isEmpty()){
            view.loadDataWithBaseURL(null, "<html dir=\"rtl\" style=\"text-align:justify; width:100%;\" lang=\"\"><body>" + content + "</body></html>", "text/html", "utf-8", null);
        }

    }



}
