package com.test.newshop1.ui.homeActivity;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.newshop1.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class BannerPagerAdapter extends PagerAdapter {
    private String images[];

    private OnItemClick listener;

    interface OnItemClick{
        void onClick(int position);
    }

    public BannerPagerAdapter( String images[], OnItemClick listener) {
        this.images = images;
        this.listener = listener;

    }

    public void setListener(OnItemClick listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        Resources resources = container.getContext().getResources();
        View itemView = layoutInflater.inflate(R.layout.image_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        switch (position){
            case 0:
                imageView.setImageDrawable(resources.getDrawable(R.drawable.banner1));
                break;
            case 1:
                imageView.setImageDrawable(resources.getDrawable(R.drawable.banner2));
                break;
            case 2:
                imageView.setImageDrawable(resources.getDrawable(R.drawable.banner3));

        }

//        Picasso.get().load(images[position])
//                .error(R.drawable.logo)
//                .placeholder(R.drawable.logo)
//                .into(imageView);

        container.addView(itemView);

        //listening to image click
        //imageView.setOnClickListener(v -> Toast.makeText(container.getContext(), "you clicked image " + (position + 1), Toast.LENGTH_LONG).show());
        imageView.setOnClickListener(view -> listener.onClick(position));
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }


}
