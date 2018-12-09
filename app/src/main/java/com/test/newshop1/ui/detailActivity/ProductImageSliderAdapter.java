package com.test.newshop1.ui.detailActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.util.Log;
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

    private Context mContext;
    private List<Image> imageUrl;
    private LayoutInflater layoutInflater;
    private OnCallback mOnCallback;
    private ImageView imageView;
    private ProgressBar loading;
    private TextView retry;

    public interface OnCallback {
        void callbackColor(int color, int position);
        void startPostponedTransition();
    }

    public ProductImageSliderAdapter(Context context, List<Image> imageUrl, OnCallback callback) {
        this.mContext = context;
        //HashSet<Image> imageSet = new HashSet<>();

        this.imageUrl = imageUrl;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mOnCallback = callback;
    }

    @Override
    public int getCount() {
        return imageUrl == null ? 0 : imageUrl.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = layoutInflater.inflate(R.layout.slider_holder, container, false);
        imageView = itemView.findViewById(R.id.image_holder);
        loading = itemView.findViewById(R.id.loading);
        retry = itemView.findViewById(R.id.retry_text);
        String src = imageUrl.get(position).getSrc();

        Picasso.get().load(ImageUtil.getThumb(src,ImageUtil.MEDIUM_SIZE))
                .error(R.drawable.bg_white)
                .placeholder(R.drawable.bg_white)
                .into(imageView);

        loadImage(src, position);

        container.addView(itemView);
        return itemView;

    }

    private void loadImage(final String src, final int position) {

        final ImageView testImageView = imageView;

//        Log.d(TAG, "Outer: "+position+" imageView      - hash: " + imageView.getDrawable().hashCode()+ " " +(imageView.getDrawable() instanceof BitmapDrawable));
//        Log.d(TAG, "Outer: "+position+" finalImageView - hash: " + testImageView.getDrawable().hashCode()+ " " +(testImageView.getDrawable() instanceof BitmapDrawable));

        Picasso.get().load(src)
                .error(R.drawable.bg_white)
                .placeholder(imageView.getDrawable())
                .into(testImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mOnCallback.startPostponedTransition();

//                        Log.d(TAG, "Inner: "+position+" imageView      - hash: " + imageView.getDrawable().hashCode()+ " " +(imageView.getDrawable() instanceof BitmapDrawable));
//                        Log.d(TAG, "Inner: "+position+" finalImageView - hash: " + testImageView.getDrawable().hashCode()+ " " +(testImageView.getDrawable() instanceof BitmapDrawable));

                        Bitmap bitmap = ((BitmapDrawable) testImageView.getDrawable()).getBitmap();
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                int color = palette.getVibrantColor(R.attr.colorPrimary);
                                mOnCallback.callbackColor(color, position);
                                notifyDataSetChanged();
                                loading.setVisibility(View.INVISIBLE);

                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        mOnCallback.startPostponedTransition();
                        loading.setVisibility(View.INVISIBLE);
                        retry.setVisibility(View.VISIBLE);

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                retry.setVisibility(View.INVISIBLE);
                                loading.setVisibility(View.VISIBLE);
                                loadImage(src, position);
                            }
                        });
                    }
                });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
