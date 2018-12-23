package com.test.newshop1.ui.homeActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.newshop1.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class BannerPagerAdapter extends PagerAdapter {
    private String images[];

    public BannerPagerAdapter( String images[]) {
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        View itemView = layoutInflater.inflate(R.layout.image_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        Picasso.get().load(images[position])
                .error(R.drawable.logo)
                .placeholder(R.drawable.logo)
                .into(imageView);

        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(v -> Toast.makeText(container.getContext(), "you clicked image " + (position + 1), Toast.LENGTH_LONG).show());

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }


}
