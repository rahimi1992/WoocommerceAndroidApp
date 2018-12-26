package com.test.newshop1.ui.homeActivity;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {


    private ViewGroup.MarginLayoutParams layoutParams;

    CustomItemDecoration(ViewGroup.MarginLayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        float density = view.getContext().getResources().getDisplayMetrics().density;
        int position = parent.getChildAdapterPosition(view);


        if (position == 0){
            outRect.right = (int)(layoutParams.rightMargin * density);
        }
        outRect.left = (int)(layoutParams.leftMargin * density);
        outRect.top = (int)(layoutParams.topMargin * density);
        outRect.bottom = (int)(layoutParams.bottomMargin * density);


    }
}
