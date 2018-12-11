package com.test.newshop1.ui.detailActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.test.newshop1.R;
import com.test.newshop1.data.database.product.Image;
import com.test.newshop1.utilities.ImageUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductImageSliderAdapter extends PagerAdapter {
    private static final String TAG = "ProductImageSliderAdapt";

    private List<Image> imageUrl;
    private OnCallback mOnCallback;


    public interface OnCallback {
        void callbackColor(int color, int position);
        void startPostponedTransition();
    }

    public ProductImageSliderAdapter(List<Image> imageUrl, OnCallback callback) {

        //HashSet<Image> imageSet = new HashSet<>();

        this.imageUrl = imageUrl;
        this.mOnCallback = callback;
    }

    @Override
    public int getCount() {
        return imageUrl == null ? 0 : imageUrl.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.slider_holder, container, false);
        String src = imageUrl.get(position).getSrc();
        ImageView imageView = itemView.findViewById(R.id.image_holder);
        Picasso.get().load(ImageUtil.getThumb(src,ImageUtil.MEDIUM_SIZE))
                .error(R.drawable.bg_white)
                .placeholder(R.drawable.bg_white)
                .into(imageView);

        loadImage(itemView,src, position);

        container.addView(itemView);
        return itemView;

    }

    private void loadImage(View itemView, final String src, final int position) {


        ImageView imageView = itemView.findViewById(R.id.image_holder);
        ProgressBar loading = itemView.findViewById(R.id.loading);
        TextView retry = itemView.findViewById(R.id.retry_text);

        Picasso.get().load(src)
                .error(imageView.getDrawable())
                .placeholder(imageView.getDrawable())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mOnCallback.startPostponedTransition();

                        loading.setVisibility(View.INVISIBLE);
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        Palette.from(bitmap).generate(palette -> {
                            int color = 0;
                            if (palette != null) {
                                color = palette.getDarkVibrantColor(R.attr.colorAccent);
                            }
                            mOnCallback.callbackColor(color, position);
                            notifyDataSetChanged();


                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        mOnCallback.startPostponedTransition();
                        loading.setVisibility(View.INVISIBLE);
                        retry.setVisibility(View.VISIBLE);

                        imageView.setOnClickListener(v -> {
                            retry.setVisibility(View.INVISIBLE);
                            loading.setVisibility(View.VISIBLE);
                            loadImage(itemView, src, position);
                        });
                    }
                });
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
