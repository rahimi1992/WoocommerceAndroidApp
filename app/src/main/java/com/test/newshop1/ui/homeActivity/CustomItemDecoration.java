package com.test.newshop1.ui.homeActivity;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {



    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        float density = view.getContext().getResources().getDisplayMetrics().density;
        int position = parent.getChildAdapterPosition(view);


        if (position == 0){
            outRect.right = (int)(160 * density);
        }
        outRect.left = (int)(4 * density);
        outRect.top = (int)(40 * density);
        outRect.bottom = (int)(5 * density);
    }
}
